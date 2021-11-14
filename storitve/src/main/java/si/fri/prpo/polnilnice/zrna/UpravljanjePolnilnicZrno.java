package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.dtos.TerminDTO;
import si.fri.prpo.polnilnice.dtos.UporabnikDTO;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.UUID;
import java.util.logging.Logger;

public class UpravljanjePolnilnicZrno {

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
        log.info("Initialized: " + UpravljanjePolnilnicZrno.class.getName() + ", UUID: " + uuid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UpravljanjePolnilnicZrno.class.getName() + ", UUID: " + uuid);
    }

    public Polnilnica ustvariPolnilnico(PolnilnicaDTO polnilnicaDTO) {
        return null;
    }

    public Polnilnica izbrisiPolnilnico(Long polnilnicaID) {
        return null;
    }

    public Uporabnik spremeniLastnika(Long polnilnicaID, UporabnikDTO uporabnikDTO) {
        return null;
    }

    public Termin dodajTerminNaPolnilnico(Long polnilnicaID, TerminDTO terminDTO) {
        return null;
    }

    public Termin izbrisiTerminIzPolnilnice(Long polnilnicaID, TerminDTO terminDTO) {
        return null;
    }
}
