package si.fri.prpo.polnilnice.zrna;

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
import java.util.logging.Logger;

@RequestScoped
public class TerminiZrno {

    @PersistenceContext(unitName = "polnilnice-jpa")
    private EntityManager em;

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UporabnikiZrno.class.getName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UporabnikiZrno.class.getName());
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void createTermin(Termin t) {
        if(t != null) {
            em.persist(t);
        }
    }

    public Termin getTermin(long id) {
        Query q = em.createNamedQuery("Termin.getByID");
        q.setParameter("id", id);
        return (Termin) q.getSingleResult();
    }

    public List<Termin> getTermini() {
        TypedQuery<Termin> q = em.createNamedQuery("Termin.getAll", Termin.class);
        return q.getResultList();
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
    public int updateTermin(Termin t) {
        Query q = em.createNamedQuery("Termin.updateTermin");
        q.setParameter("id", t.getId())
                .setParameter("uporabnik", t.getUporabnik())
                .setParameter("polnilnica", t.getPolnilnica())
                .setParameter("zac_termina", t.getZacetek_termina())
                .setParameter("kon_termina", t.getKonec_termina());

        return q.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int deleteTermin(long id) {
        Query q = em.createNamedQuery("Termin.deleteTermin");
        q.setParameter("id", id);
        return q.executeUpdate();
    }
}
