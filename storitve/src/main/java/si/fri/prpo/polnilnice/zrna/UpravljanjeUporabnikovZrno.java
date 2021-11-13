package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.dtos.TerminDTO;
import si.fri.prpo.polnilnice.dtos.UporabnikDTO;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeUporabnikovZrno {

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private TerminiZrno terminiZrno;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UpravljanjeUporabnikovZrno.class.getName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UpravljanjeUporabnikovZrno.class.getName());
    }

    public Uporabnik ustvariUporabnika(UporabnikDTO uporabnikDTO) {

        if(uporabnikDTO.getIme().isEmpty() || uporabnikDTO.getPriimek().isEmpty() || uporabnikDTO.getUporabnisko_ime().isEmpty() ||uporabnikDTO.getEmail().isEmpty()) {
            log.info("Ne morem ustvariti uporabnika. Manjkajo obvezni podatki");
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
            return true;
        }

        return uporabnikiZrno.deleteUporabnik(uporabnikID);
    }

    public Termin dodajTerminUporabniku(Long uporabnikID, TerminDTO terminDTO) {
        return null;
    }

    public Termin izbrisiTerminUporabniku(Long uporabnikID, TerminDTO terminDTO) {
        return null;
    }
}
