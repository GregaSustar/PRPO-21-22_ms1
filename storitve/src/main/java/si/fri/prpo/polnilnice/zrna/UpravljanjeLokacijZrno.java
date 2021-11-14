package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.dtos.LokacijaDTO;
import si.fri.prpo.polnilnice.dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Polnilnica;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeLokacijZrno {

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    private static final UUID uuid = UUID.randomUUID();

    @Inject
    private LokacijeZrno lokacijeZrno;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UpravljanjeLokacijZrno.class.getName() + ", UUID: " + uuid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UpravljanjeLokacijZrno.class.getName() + ", UUID: " + uuid);
    }

    public Lokacija ustvariLokacijo(LokacijaDTO lokacijaDTO) {

        Lokacija lokacija = lokacijeZrno.getLokacija(lokacijaDTO.getNaslov());

        if(lokacija != null) {
            log.info("Lokacija s tem naslovom že obstaja");
            return lokacija;
        }

        if(lokacijaDTO.getDrzava().isEmpty() ||lokacijaDTO.getMesto().isEmpty() || lokacijaDTO.getNaslov().isEmpty() || lokacijaDTO.getPostna_st() == 0) {
            log.info("Ne morem ustvariti lokacije. Niso vnešeni vsi podatki");
            return null;
        }

        lokacija = new Lokacija();
        lokacija.setDrzava(lokacijaDTO.getDrzava());
        lokacija.setMesto(lokacijaDTO.getMesto());
        lokacija.setNaslov(lokacijaDTO.getNaslov());
        lokacija.setPostna_st(lokacijaDTO.getPostna_st());

        return lokacijeZrno.createLokacija(lokacija);
    }

    public boolean izbrisiLokacijo(Long lokacijaID) {

        Lokacija lokacija = lokacijeZrno.getLokacija(lokacijaID);

        if(lokacija == null) {
            log.info("Ne morem izbrisati Lokacije. Lokacija z id-jem: " + lokacijaID + "ne obstaja");
            return false;
        }

        return lokacijeZrno.deleteLokacija(lokacijaID);
    }

    @Transactional
    public Polnilnica dodajPolnilnicoNaLokacijo(Long lokacijaID, Long polnilnicaID) {

        Lokacija lokacija = lokacijeZrno.getLokacija(lokacijaID);
        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(polnilnicaID);

        if(lokacija == null) {
            log.info("Ne morem dodati Polnilnice na Lokacijo. Lokacija z id-jem: " + lokacijaID + "ne obstaja");
            return null;
        }

        if(polnilnica == null) {
            log.info("Ne morem dodati Polnilnice na Lokacijo. Polnilnica z id-jem: " + polnilnicaID + "ne obstaja");
            return null;
        }

        lokacija.getPolnilnice().add(polnilnica);
        lokacijeZrno.updateLokacija(lokacijaID, lokacija);

        return polnilnica;
    }

    @Transactional
    public boolean izbrisiPolnilnicoIzLokacije(Long lokacijaID, Long polnilnicaID) {

        Lokacija lokacija = lokacijeZrno.getLokacija(lokacijaID);
        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(polnilnicaID);

        if(lokacija == null) {
            log.info("Ne morem izbrisati Polnilnice iz Lokacijo. Lokacija z id-jem: " + lokacijaID + "ne obstaja");
            return false;
        }

        if(lokacija.getPolnilnice().isEmpty()) {
            log.info("Ne morem izbrisati Polnilnice iz Lokacijo. Lokacija nima nobene Polnilnice");
            return false;
        }

        if(polnilnica == null) {
            log.info("Ne morem izbrisati Polnilnice iz Lokacijo. Polnilnica z id-jem: " + polnilnicaID + "ne obstaja");
            return false;
        }

        lokacija.getPolnilnice().remove(polnilnica);
        lokacijeZrno.updateLokacija(lokacijaID, lokacija);

        return true;
    }

}
