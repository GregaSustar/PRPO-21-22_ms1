package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UporabnikiZrno {

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
    public Uporabnik createUporabnik(Uporabnik u) {
        if(u != null) {
            em.persist(u);
            return u;
        }
        return null;
    }

    public Uporabnik getUporabnik(String upr_ime) {
        Query q = em.createNamedQuery("Uporabnik.getByUporabniskoIme");
        q.setParameter("upr_ime", upr_ime);

        Uporabnik result = null;
        try {
            result = (Uporabnik) q.getSingleResult();
        } catch (NoResultException e) {}

        return result;
    }

    public Uporabnik getUporabnikByEmail(String email) {
        Query q = em.createNamedQuery("Uporabnik.getByEmail");
        q.setParameter("email", email);

        Uporabnik result = null;
        try {
            result = (Uporabnik) q.getSingleResult();
        } catch (NoResultException e) {}

        return result;
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
    public Uporabnik updateUporabnik(Long id, Uporabnik u) {
        Uporabnik org_u = getUporabnik(id);
        if(org_u != null) {
            org_u.setIme(u.getIme());
            org_u.setPriimek(u.getPriimek());
            org_u.setEmail(u.getEmail());
            org_u.setRezervacija(u.getRezervacija());
            org_u.setUporabnisko_ime(u.getUporabnisko_ime());
            return org_u;
        }
        return null;
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
