package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
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

@ApplicationScoped
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
    public void createUporabnik(Uporabnik u) {
        if(u != null) {
            em.persist(u);
        }
    }

    public Uporabnik getUporabnik(String upr_ime) {
        Query q = em.createNamedQuery("Uporabnik.getByUporabniskoIme");
        return (Uporabnik) q.getSingleResult();
    }

    public Uporabnik getUporabnik(long id) {
        return null;
    }

    public List<Uporabnik> getUporabniki() {
        Query q = em.createNamedQuery("Uporabnik.getAll");
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
    public long updateUporabnik(Uporabnik u) {
        Query q = em.createNamedQuery("Uporabnik.updateUporabnik");
        q.setParameter("id", u.getId())
         .setParameter("ime", u.getIme())
         .setParameter("priimek", u.getPriimek())
         .setParameter("upr_ime", u.getUporabnisko_ime())
         .setParameter("rez", u.getRezervacija());

        return q.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public long deleteUporabnik(long id) {
        Query q = em.createNamedQuery("Uporabnik.deleteUporabnik");
        return q.setParameter("id", id).executeUpdate();
    }
}
