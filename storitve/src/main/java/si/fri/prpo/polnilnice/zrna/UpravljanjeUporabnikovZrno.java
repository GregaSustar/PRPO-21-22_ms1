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
        return null;
    }

    public Uporabnik izbrisiUporabnika(Long uporabnikID) {
        return null;
    }

    public Termin dodajTerminUporabniku(Long uporabnikID, TerminDTO terminDTO) {
        return null;
    }

    public Termin izbrisiTerminUporabniku(Long uporabnikID, TerminDTO terminDTO) {
        return null;
    }
}
