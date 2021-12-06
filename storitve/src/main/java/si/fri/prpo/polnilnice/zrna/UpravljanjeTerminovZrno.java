package si.fri.prpo.polnilnice.zrna;

import jdk.vm.ci.meta.Local;
import si.fri.prpo.polnilnice.dtos.TerminDTO;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import si.fri.prpo.polnilnice.izjeme.NeveljavenTerminIzjema;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeTerminovZrno {

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    private static final UUID uuid = UUID.randomUUID();

    private static final Long MAX_TERMIN_DURATION_IN_MIN = 120L;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private TerminiZrno terminiZrno;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno;

    @Inject
    private UpravljanjePolnilnicZrno upravljanjePolnilnicZrno;

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UpravljanjeTerminovZrno.class.getName() + ", UUID: " + uuid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UpravljanjeTerminovZrno.class.getName() + ", UUID: " + uuid);
    }

    public Termin ustvariTermin(TerminDTO terminDTO) {

        Uporabnik uporabnik = terminDTO.getUporabnik();
        Polnilnica polnilnica = terminDTO.getPolnilnica();

        if(uporabnik == null || uporabnikiZrno.getUporabnik(uporabnik.getId()) == null) {
            log.info("Ne morem ustvariti Termina. Uporabnik ne obstaja");
            return null;
        }

        if(polnilnica == null || polnilniceZrno.getPolnilnica(polnilnica.getId()) == null) {
            log.info("Ne morem ustvariti Termina. Polnilnica ne obstaja");
            return null;
        }

        if(terminDTO.getKonec_termina() == null || terminDTO.getZacetek_termina() == null) {
            log.info("Ne morem ustvariti Termina. Ni podanega zacetka/konca termina");
            return null;
        }

        Termin termin = new Termin();
        termin.setUporabnik(uporabnik);
        termin.setPolnilnica(polnilnica);
        termin.setZacetek_termina(terminDTO.getZacetek_termina());
        termin.setKonec_termina(terminDTO.getKonec_termina());

        if(!isAvailableAtPolnilnica(polnilnica, termin) || !hasValidInterval(termin)) {
            log.info("Nevaliden termin.");
            throw new NeveljavenTerminIzjema("Neveljaven Termin");
            //return null;
        }

        termin = terminiZrno.createTermin(termin);

        // add termin to polnilnica
        upravljanjePolnilnicZrno.dodajTerminNaPolnilnico(polnilnica.getId(), termin.getId());
        // add termin to uporabnik
        upravljanjeUporabnikovZrno.dodajTerminUporabniku(uporabnik.getId(), termin.getId());

        return termin;
    }

    public boolean izbrisiTermin(Long terminID) {

        Termin termin = terminiZrno.getTermin(terminID);

        if(termin == null) {
            log.info("Ne morem izbrisati Termina. Termin z id-jem: " + terminID + " ne obstaja");
            return false;
        }

        boolean res = terminiZrno.deleteTermin(terminID);

        // izbrisi termin uporabniku
        upravljanjeUporabnikovZrno.izbrisiTerminUporabniku(termin.getUporabnik().getId());
        // izbrisi termin polnilnici
        upravljanjePolnilnicZrno.izbrisiTerminIzPolnilnice(termin.getPolnilnica().getId(), terminID);

        return res;
    }

    public boolean hasValidInterval(Termin t) {
        LocalDateTime from = t.getZacetek_termina();
        LocalDateTime to = t.getKonec_termina();
        return to.isAfter(from) && Duration.between(from, to).toMinutes() <= MAX_TERMIN_DURATION_IN_MIN;
    }

    public boolean isOverlapping(Termin t1, Termin t2) {
        return (!t1.getKonec_termina().isBefore(t2.getZacetek_termina()) && !t1.getZacetek_termina().isAfter(t2.getKonec_termina()));
    }

    public boolean isAvailableAtPolnilnica(Polnilnica p, Termin t) {

        List<Termin> termini = p.getTermini();
        if(termini == null)
            return true;

        for (Termin iter_t : termini) {
            if(isOverlapping(t, iter_t))
                return false;
        }

        return true;
    }
}
