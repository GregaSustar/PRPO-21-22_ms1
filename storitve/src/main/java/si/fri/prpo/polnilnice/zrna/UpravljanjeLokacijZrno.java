package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.dtos.LokacijaDTO;
import si.fri.prpo.polnilnice.dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Polnilnica;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.logging.Logger;

public class UpravljanjeLokacijZrno {

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private TerminiZrno terminiZrno;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UpravljanjeLokacijZrno.class.getName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UpravljanjeLokacijZrno.class.getName());
    }

    public Lokacija ustvariLokacijo(LokacijaDTO lokacijaDTO) {
        return null;
    }

    public Lokacija izbrisiLokacijo(Long lokacijaID) {
        return null;
    }

    public Polnilnica dodajPolnilnicoNaLokacijo(Long polnilnicaID, PolnilnicaDTO polnilnicaDTO) {
        return null;
    }

    public Polnilnica izbrisiPolnilnicoIzLokacije(Long polnilnicaID, PolnilnicaDTO polnilnicaDTO) {
        return null;
    }
}
