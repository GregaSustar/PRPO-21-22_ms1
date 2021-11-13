package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
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
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
public class PolnilniceZrno {

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
    public boolean createPolnilnica(Polnilnica p) {
        if(p != null) {
            em.persist(p);
            return true;
        }
        return false;
    }

    public Polnilnica getPolnilnica(Long id) {
        return em.find(Polnilnica.class, id);
    }

    public List<Polnilnica> getPolnilnice() {
        TypedQuery<Polnilnica> q = em.createNamedQuery("Polnilnica.getAll", Polnilnica.class);
        return q.getResultList();
    }

    public List<Polnilnica> getPolnilnice(Uporabnik lastnik) {
        TypedQuery<Polnilnica> q = em.createNamedQuery("Polnilnica.getAllOfLastnik", Polnilnica.class);
        q.setParameter("id", lastnik.getId());
        return q.getResultList();
    }

    public List<Polnilnica> getPolnilnice(Lokacija lokacija) {
        TypedQuery<Polnilnica> q = em.createNamedQuery("Polnilnica.getAllInMesto", Polnilnica.class);
        q.setParameter("mesto", lokacija.getMesto());
        return q.getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean updatePolnilnica(Long id, Polnilnica p) {
        if(getPolnilnica(id) != null) {
            em.refresh(p);
            return true;
        }
        return false;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean deletePolnilnica(Long id) {
        Polnilnica p = getPolnilnica(id);
        if(p != null) {
            em.remove(p);
            return true;
        }
        return false;
    }
}
