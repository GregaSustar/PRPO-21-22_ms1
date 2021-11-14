package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class LokacijeZrno {

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
    public Lokacija createLokacija(Lokacija l) {
        if(l != null) {
            em.persist(l);
            return l;
        }
        return null;
    }

    public Lokacija getLokacija(Long id) {
        return em.find(Lokacija.class, id);
    }

    public Lokacija getLokacija(String naslov) {
        Query q = em.createNamedQuery("Lokacija.findByNaslov");
        q.setParameter("naslov", naslov);

        Lokacija result = null;
        try {
            result = (Lokacija) q.getSingleResult();
        } catch (NoResultException e) {}

        return result;
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
    public Lokacija updateLokacija(Long id, Lokacija l) {
        Lokacija org_l = getLokacija(id);
        if(org_l != null) {
            org_l.setDrzava(l.getDrzava());
            org_l.setMesto(l.getMesto());
            org_l.setNaslov(l.getNaslov());
            org_l.setPostna_st(l.getPostna_st());
            org_l.setPolnilnice(l.getPolnilnice());
            return org_l;
        }
        return null;
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
