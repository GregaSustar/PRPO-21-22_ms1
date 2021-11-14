package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.dtos.TerminDTO;
import si.fri.prpo.polnilnice.dtos.UporabnikDTO;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjePolnilnicZrno {

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    private static final UUID uuid = UUID.randomUUID();

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private TerminiZrno terminiZrno;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @Inject
    private UpravljanjeLokacijZrno upravljanjeLokacijZrno;

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UpravljanjePolnilnicZrno.class.getName() + ", UUID: " + uuid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UpravljanjePolnilnicZrno.class.getName() + ", UUID: " + uuid);
    }

    public Polnilnica ustvariPolnilnico(PolnilnicaDTO polnilnicaDTO) {

        if(polnilnicaDTO.getIme().isEmpty() || polnilnicaDTO.getLastnik() == null || polnilnicaDTO.getLokacija() == null ||
            polnilnicaDTO.getCas_odprtja() == null || polnilnicaDTO.getCas_zaprtja() == null || polnilnicaDTO.getCena_polnjenja() == 0.0D ||
            polnilnicaDTO.getMoc_v_kW() == 0.0d || polnilnicaDTO.getVrsta_toka() == null) {

            log.info("Ne morem ustvariti Polnilnice. Niso vnešeni vsi podatki");
            return null;
        }

        Polnilnica polnilnica = new Polnilnica();
        polnilnica.setIme(polnilnicaDTO.getIme());
        polnilnica.setLastnik(polnilnicaDTO.getLastnik());
        polnilnica.setLokacija(polnilnicaDTO.getLokacija());
        polnilnica.setCas_odprtja(polnilnicaDTO.getCas_odprtja());
        polnilnica.setCas_zaprtja(polnilnicaDTO.getCas_zaprtja());
        polnilnica.setCena_polnjenja(polnilnicaDTO.getCena_polnjenja());
        polnilnica.setMoc_v_kW(polnilnicaDTO.getMoc_v_kW());
        polnilnica.setVrsta_toka(polnilnicaDTO.getVrsta_toka());

        // add polnilnica to Lokacija
        upravljanjeLokacijZrno.dodajPolnilnicoNaLokacijo(polnilnica.getLokacija().getId(), polnilnica.getId());

        return polnilniceZrno.createPolnilnica(polnilnica);
    }

    public boolean izbrisiPolnilnico(Long polnilnicaID) {

        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(polnilnicaID);

        if(polnilnica == null) {
            log.info("Ne morem izbrisati polnilnice. Polnilnica z id-jem: " + polnilnicaID + " ne obstaja");
            return false;
        }

        upravljanjeLokacijZrno.izbrisiPolnilnicoIzLokacije(polnilnica.getLokacija().getId(), polnilnica.getId());

        return polnilniceZrno.deletePolnilnica(polnilnicaID);
    }

    public Polnilnica posodobiOsnovnePodatkePolnilnice(Long polnilnicaID, PolnilnicaDTO polnilnicaDTO) {

        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(polnilnicaID);

        if(polnilnica == null) {
            log.info("Ne morem posodobiti podatkov polnilnice. Polnilnica z id-jem: " + polnilnicaID + " ne obstaja");
            return null;
        }

        if(polnilnicaDTO.getIme() != null) {
            polnilnica.setIme(polnilnicaDTO.getIme());
        }

        if(polnilnicaDTO.getMoc_v_kW() != null) {
            polnilnica.setMoc_v_kW(polnilnicaDTO.getMoc_v_kW());
        }

        if(polnilnicaDTO.getCas_odprtja() != null) {
            polnilnica.setCas_odprtja(polnilnicaDTO.getCas_odprtja());
        }

        if(polnilnicaDTO.getCas_zaprtja() != null) {
            polnilnica.setCas_zaprtja(polnilnicaDTO.getCas_zaprtja());
        }

        if(polnilnicaDTO.getCena_polnjenja() != null) {
            polnilnica.setCena_polnjenja(polnilnicaDTO.getCena_polnjenja());
        }

        if(polnilnicaDTO.getVrsta_toka() != null) {
            polnilnica.setVrsta_toka(polnilnicaDTO.getVrsta_toka());
        }

        return polnilniceZrno.updatePolnilnica(polnilnicaID, polnilnica);
    }

    @Transactional
    public Uporabnik spremeniLastnika(Long polnilnicaID, Long uporabnikID) {

        Uporabnik lastnik = uporabnikiZrno.getUporabnik(uporabnikID);
        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(polnilnicaID);

        if(polnilnica == null) {
            log.info("Ne morem spremeniti Lastnika. Polnilnica z id-jem: " + polnilnicaID + " ne obstaja");
            return null;
        }

        if(lastnik == null) {
            log.info("Ne morem spremeniti Lastnika. Uporabnik z id-jem: " + uporabnikID + " ne obstaja");
            return null;
        }

        polnilnica.setLastnik(lastnik);
        polnilniceZrno.updatePolnilnica(polnilnicaID, polnilnica);

        return lastnik;
    }

    // spremeniLokacjio()

    @Transactional
    public Termin dodajTerminNaPolnilnico(Long polnilnicaID, Long terminID) {

        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(polnilnicaID);
        Termin termin = terminiZrno.getTermin(terminID);

        if(polnilnica == null) {
            log.info("Ne morem dodati Termina na Polnilnico. Polnilnica z id-jem: " + polnilnicaID + " ne obstaja");
            return null;
        }

        if(termin == null) {
            log.info("Ne morem dodati Termina na Polnilnico. Termin z id-jem: " + terminID + " ne obstaja");
            return null;
        }

        polnilnica.getTermini().add(termin);
        polnilniceZrno.updatePolnilnica(polnilnicaID, polnilnica);

        return termin;
    }

    @Transactional
    public boolean izbrisiTerminIzPolnilnice(Long polnilnicaID, Long terminID) {

        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(polnilnicaID);
        Termin termin = terminiZrno.getTermin(terminID);

        if(polnilnica == null) {
            log.info("Ne morem izbrisati Termina iz Polnilnice. Polnilnica z id-jem: " + polnilnicaID + " ne obstaja");
            return false;
        }

        if(polnilnica.getTermini().isEmpty()) {
            log.info("Ne morem izbrisati Termina iz Polnilnice. Polnilnica nima nobenega termina");
            return false;
        }

        if(termin == null) {
            log.info("Ne morem izbrisati Termina iz Polnilnico. Termin z id-jem: " + terminID + " ne obstaja");
            return false;
        }

        polnilnica.getTermini().remove(termin);
        polnilniceZrno.updatePolnilnica(polnilnicaID, polnilnica);

        return true;
    }
}
