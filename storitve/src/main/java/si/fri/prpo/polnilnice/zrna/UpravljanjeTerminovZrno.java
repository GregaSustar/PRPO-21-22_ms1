package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.dtos.TerminDTO;
import si.fri.prpo.polnilnice.entitete.Termin;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.logging.Logger;

public class UpravljanjeTerminovZrno {

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private TerminiZrno terminiZrno;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UpravljanjeTerminovZrno.class.getName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UpravljanjeTerminovZrno.class.getName());
    }

    public Termin ustvariTermin(TerminDTO terminDTO) {
        return null;
    }

    public Termin izbrisiTermin(Long terminID) {
        return null;
    }
}
