package si.fri.prpo.polnilnice.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class TerminiZrno {

    @PersistenceContext(unitName = "polnilnice-jpa")
    private EntityManager em;

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    private static final UUID uuid = UUID.randomUUID();

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UporabnikiZrno.class.getName() + ", UUID: " + uuid);
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UporabnikiZrno.class.getName() + ", UUID: " + uuid);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Termin createTermin(Termin t) {
        if(t != null) {
            em.persist(t);
            return t;
        }
        return null;
    }

    public Termin getTermin(Long id) {
        return em.find(Termin.class, id);
    }

    public List<Termin> getTermini() {
        TypedQuery<Termin> q = em.createNamedQuery("Termin.getAll", Termin.class);
        return q.getResultList();
    }

    // get termini with query parameters
    public List<Termin> getTermini(QueryParameters query) {
        return JPAUtils.queryEntities(em, Termin.class, query);
    }

    public Long getTerminiCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Termin.class, query);
    }

    public List<Termin> getTermini(Polnilnica p) {
        TypedQuery<Termin> q = em.createNamedQuery("Termin.getAllForPolnilnica", Termin.class);
        q.setParameter("polnilnica", p);
        return q.getResultList();
    }

    public List<Termin> getTermini(Uporabnik u) {
        TypedQuery<Termin> q = em.createNamedQuery("Termin.getAllForUporabnik", Termin.class);
        q.setParameter("id", u. getId());
        return q.getResultList();
    }

    public List<Termin> getTerminiBefore(LocalDateTime time) {
        TypedQuery<Termin> q = em.createNamedQuery("Termin.getAllBeforeTime", Termin.class);
        q.setParameter("kon_termina", time);
        return q.getResultList();
    }

    public List<Termin> getTerminiAfter(LocalDateTime time) {
        TypedQuery<Termin> q = em.createNamedQuery("Termin.getAllAfterTime", Termin.class);
        q.setParameter("zac_termina", time);
        return q.getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Termin updateTermin(Long id, Termin t) {
        Termin org_t = getTermin(id);
        if(org_t != null) {
            org_t.setPolnilnica(t.getPolnilnica());
            org_t.setUporabnik(t.getUporabnik());
            org_t.setZacetek_termina(t.getZacetek_termina());
            org_t.setKonec_termina(t.getKonec_termina());
            return org_t;
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean deleteTermin(Long id) {
        Termin t = getTermin(id);
        if(t != null) {
            em.remove(t);
            return true;
        }
        return false;
    }
}
