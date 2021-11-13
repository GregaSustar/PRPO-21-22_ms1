package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
public class UporabnikiZrno {

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
    public boolean createUporabnik(Uporabnik u) {
        if(u != null) {
            em.persist(u);
            return true;
        }
        return false;
    }

    public Uporabnik getUporabnik(String upr_ime) {
        Query q = em.createNamedQuery("Uporabnik.getByUporabniskoIme");
        q.setParameter("upr_ime", upr_ime);
        return (Uporabnik) q.getSingleResult();
    }

    public Uporabnik getUporabnik(Long id) {
        return em.find(Uporabnik.class, id);
    }

    public List<Uporabnik> getUporabniki() {
        TypedQuery<Uporabnik> q = em.createNamedQuery("Uporabnik.getAll", Uporabnik.class);
        return q.getResultList();
    }

    public List<Uporabnik> getAllWithTermin() {
        TypedQuery<Uporabnik> q = em.createNamedQuery("Uporabnik.getAllWithTermin", Uporabnik.class);
        return q.getResultList();
    }

    public List<Uporabnik> getUporabnikiCriteria() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Uporabnik> cq = cb.createQuery(Uporabnik.class);
        Root<Uporabnik> upr = cq.from(Uporabnik.class);
        TypedQuery<Uporabnik> tq = em.createQuery(cq);
        return tq.getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean updateUporabnik(Long id, Uporabnik u) {
        if(getUporabnik(id) != null) {
            em.refresh(u);
            return true;
        }
        return false;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean deleteUporabnik(Long id) {
        Uporabnik u = getUporabnik(id);
        if(u != null) {
            em.remove(u);
            return true;
        }
        return false;
    }
}
