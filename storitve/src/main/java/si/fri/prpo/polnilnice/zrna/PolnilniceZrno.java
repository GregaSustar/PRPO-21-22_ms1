package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
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
    public void createPolnilnica(Polnilnica p) {
        if(p != null) {
            em.persist(p);
        }
    }

    public Polnilnica getPolnilnica(long id) {
        Query q = em.createNamedQuery("Polnilnica.getByID");
        q.setParameter("id", id);
        return (Polnilnica) q.getSingleResult();
    }

    public List<Polnilnica> getPolnilnice() {
        Query q = em.createNamedQuery("Polnilnica.getAll");
        return q.getResultList();
    }

    public List<Polnilnica> getPolnilnice(Uporabnik lastnik) {
        Query q = em.createNamedQuery("Polnilnica.getAllOfLastnik");
        q.setParameter("id", lastnik.getId());
        return q.getResultList();
    }

    public List<Polnilnica> getPolnilnice(Lokacija lokacija) {
        Query q = em.createNamedQuery("Polnilnica.getAllInMesto");
        q.setParameter("mesto", lokacija.getMesto());
        return q.getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int updatePolnilnica(Polnilnica p) {
        Query q = em.createNamedQuery("Polnilnica.updatePolnilnica");
        q.setParameter("ime", p.getIme())
                .setParameter("lastnik", p.getLastnik())
                .setParameter("lokacija", p.getLokacija())
                .setParameter("cena", p.getCena_polnjenja())
                .setParameter("moc", p.getMoc_v_kW())
                .setParameter("tok", p.getVrsta_toka())
                .setParameter("cas_odprtja", p.getCas_odprtja())
                .setParameter("cas_zaprtja", p.getCas_zaprtja());

        return q.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int deletePolnilnica(long id) {
        Query q = em.createNamedQuery("Polnilnica.deletePolnilnica");
        q.setParameter("id", id);
        return q.executeUpdate();
    }
}
