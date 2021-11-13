package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Lokacija;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
public class LokacijeZrno {

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
    public boolean createLokacija(Lokacija l) {
        if(l != null) {
            em.persist(l);
            return true;
        }
        return false;
    }

    public Lokacija getLokacija(Long id) {
        return em.find(Lokacija.class, id);
    }

    public Lokacija getLokacija(String naslov) {
        Query q = em.createNamedQuery("Lokacija.findByNaslov");
        q.setParameter("naslov", naslov);
        return (Lokacija) q.getSingleResult();
    }

    public List<Lokacija> getLokacije() {
        TypedQuery<Lokacija> q = em.createNamedQuery("Lokacija.getAll", Lokacija.class);
        return q.getResultList();
    }

    public List<Lokacija> getLokacije(Integer postna_st) {
        TypedQuery<Lokacija> q = em.createNamedQuery("Lokacija.getAllByPostnaSt", Lokacija.class);
        q.setParameter("postna_st", postna_st);
        return q.getResultList();
    }

    public List<Lokacija> getLokacijeByDrzava(String drzava) {
        TypedQuery<Lokacija> q = em.createNamedQuery("Lokacija.getAllByDrzava", Lokacija.class);
        q.setParameter("drzava", drzava);
        return q.getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean updateLokacija(Long id, Lokacija l) {
        if(getLokacija(id) != null) {
            em.refresh(l);
            return true;
        }
        return false;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean deleteLokacija(Long id) {
        Lokacija l = getLokacija(id);
        if(l != null) {
            em.remove(l);
            return true;
        }
        return false;
    }
}
