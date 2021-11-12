package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Termin;

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
    public void createLokacija(Lokacija l) {
        if(l != null) {
            em.persist(l);
        }
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
    public int updateLokacija(Lokacija l) {
        Query q = em.createNamedQuery("Lokacija.updateLokacija");
        q.setParameter("id", l.getId())
                .setParameter("drzava", l.getDrzava())
                .setParameter("mesto", l.getMesto())
                .setParameter("naslov", l.getNaslov())
                .setParameter("postna", l.getPostna_st());

        return q.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int deleteLokacija(long id) {
        Query q = em.createNamedQuery("Lokacija.deleteLokacija");
        q.setParameter("id", id);
        return q.executeUpdate();
    }
}
