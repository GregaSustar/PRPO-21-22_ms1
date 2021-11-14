package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.dtos.TerminDTO;
import si.fri.prpo.polnilnice.dtos.UporabnikDTO;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeUporabnikovZrno {

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    private static final UUID uuid = UUID.randomUUID();

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private TerminiZrno terminiZrno;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UpravljanjeUporabnikovZrno.class.getName() + ", UUID: " + uuid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UpravljanjeUporabnikovZrno.class.getName() + ", UUID: " + uuid);
    }

    public Uporabnik ustvariUporabnika(UporabnikDTO uporabnikDTO) {

        if(uporabnikDTO.getIme().isEmpty() || uporabnikDTO.getPriimek().isEmpty() || uporabnikDTO.getUporabnisko_ime().isEmpty() || uporabnikDTO.getEmail().isEmpty()) {
            log.info("Ne morem ustvariti uporabnika. Manjkajo obvezni podatki");
            return null;
        }

        if(uporabnikiZrno.getUporabnik(uporabnikDTO.getUporabnisko_ime()) != null) {
            log.info("Uporabnik s takšnim uporabniskim imenom ze obstaja. Prosim izbreite drugo uporabnisko ime.");
            return null;
        }

        if(uporabnikiZrno.getUporabnikByEmail(uporabnikDTO.getEmail()) != null) {
            log.info("Uporabnik s takšnim email-om že obstaja. Prosim vnesite drug email.");
            return null;
        }

        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setIme(uporabnikDTO.getIme());
        uporabnik.setPriimek(uporabnikDTO.getPriimek());
        uporabnik.setUporabnisko_ime(uporabnikDTO.getUporabnisko_ime());
        uporabnik.setEmail(uporabnikDTO.getEmail());

        return uporabnikiZrno.createUporabnik(uporabnik);
    }

    public boolean izbrisiUporabnika(Long uporabnikID) {

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnikID);

        if(uporabnik == null) {
            log.info("Ne morem izbrisati uporabnika. Uporabnik z id-jem: " + uporabnikID + " ne obstaja");
            return false;
        }

        return uporabnikiZrno.deleteUporabnik(uporabnikID);
    }

    public Uporabnik posodobiUporabnika(Long uporabnikID, UporabnikDTO uporabnikDTO) {

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnikID);

        if(uporabnik == null) {
            log.info("Ne morem posodobiti uporabnika. Uporabnik z id-jem: " + uporabnikID + " ne obstaja");
            return null;
        }

        if(!uporabnikDTO.getIme().isEmpty())
            uporabnik.setIme(uporabnikDTO.getIme());

        if(!uporabnikDTO.getPriimek().isEmpty())
            uporabnik.setPriimek(uporabnikDTO.getPriimek());

        if(!uporabnikDTO.getUporabnisko_ime().isEmpty()) {
            if(uporabnikiZrno.getUporabnik(uporabnikDTO.getUporabnisko_ime()) != null) {
                log.info("Uporabnik s takšnim uporabniskim imenom ze obstaja. Prosim izbreite drugo uporabnisko ime.");
                return null;
            }

            uporabnik.setUporabnisko_ime(uporabnikDTO.getUporabnisko_ime());
        }

        if(!uporabnikDTO.getEmail().isEmpty()) {
            Uporabnik emailCheck = uporabnikiZrno.getUporabnikByEmail(uporabnikDTO.getEmail());
            if(emailCheck != null && emailCheck.getId().equals(uporabnikID)) {
                log.info("Uporabnik s takšnim email-om že obstaja. Prosim vnesite drug email.");
                return null;
            }

            uporabnik.setEmail(uporabnikDTO.getEmail());
        }

        return uporabnikiZrno.updateUporabnik(uporabnikID, uporabnik);
    }

    public Termin dodajTerminUporabniku(Long uporabnikID, TerminDTO terminDTO) {

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnikID);
        Termin termin = terminiZrno.getTermin(terminDTO.getId());

        if(uporabnik == null) {
            log.info("Ne morem dodati Termina Uporabniku. Uporabnik z id-jem: " + uporabnikID + " ne obstaja");
            return null;
        }

        if(termin == null) {
            log.info("Ne morem dodati Termina Uporabniku. Termin ne obstaja");
            return null;
        }

        uporabnik.setRezervacija(termin);
        uporabnikiZrno.updateUporabnik(uporabnikID, uporabnik);
        termin.setUporabnik(uporabnik);

        return termin;
    }

    public boolean izbrisiTerminUporabniku(Long uporabnikID) {

        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(uporabnikID);

        if(uporabnik == null) {
            log.info("Ne morem izbrisati Termina Uporabniku. Uporabnik z id-jem: " + uporabnikID + " ne obstaja");
            return false;
        }

        if(uporabnik.getRezervacija() == null) {
            log.info("Ne morem izbrisati Termina Uporabniku. Uporabnik z id-jem: " + uporabnikID + " nima rezerviranega termina ");
            return false;
        }

        Termin termin = uporabnik.getRezervacija();
        uporabnik.setRezervacija(null);
        return terminiZrno.deleteTermin(termin.getId());
    }
}
