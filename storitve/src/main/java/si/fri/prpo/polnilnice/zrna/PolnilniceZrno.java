package si.fri.prpo.polnilnice.zrna;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.polnilnice.dtos.OcenaDTO;
import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class PolnilniceZrno {

    @PersistenceContext(unitName = "polnilnice-jpa")
    private EntityManager em;

    private static final Logger log = Logger.getLogger(UporabnikiZrno.class.getName());

    private static final UUID uuid = UUID.randomUUID();

    private Client httpClient;

    private String baseUrl;

    @PostConstruct
    private void init() {
        log.info("Initialized: " + UporabnikiZrno.class.getName() + ", UUID: " + uuid);

        httpClient = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance().get("integrations.ocenepolnilnic.baseurl").get();
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroying: " + UporabnikiZrno.class.getName() + ", UUID: " + uuid);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Polnilnica createPolnilnica(Polnilnica p) {
        if(p != null) {
            em.persist(p);
            return p;
        }
        return null;
    }

    public Polnilnica getPolnilnica(Long id) {
        return em.find(Polnilnica.class, id);
    }

    public List<Polnilnica> getPolnilnice() {
        TypedQuery<Polnilnica> q = em.createNamedQuery("Polnilnica.getAll", Polnilnica.class);
        return q.getResultList();
    }

    // get polnilnice with query parameters
    public List<Polnilnica> getPolnilnice(QueryParameters query) {
        return JPAUtils.queryEntities(em, Polnilnica.class, query);
    }

    public Long getPolnilniceCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Polnilnica.class, query);
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
    public Polnilnica updatePolnilnica(Long id, Polnilnica p) {
        Polnilnica org_p = getPolnilnica(id);
        if(org_p != null) {
            org_p.setIme(p.getIme());
            org_p.setLastnik(p.getLastnik());
            org_p.setLokacija(p.getLokacija());
            org_p.setMoc_v_kW(p.getMoc_v_kW());
            org_p.setVrsta_toka(p.getVrsta_toka());
            org_p.setCas_odprtja(p.getCas_odprtja());
            org_p.setCas_zaprtja(p.getCas_zaprtja());
            org_p.setCena_polnjenja(p.getCena_polnjenja());
            org_p.setTermini(p.getTermini());
            return p;
        }
        return null;
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

    public List<OcenaDTO> pridobiOcene(Long id) throws WebApplicationException, ProcessingException {
        return httpClient
                .target(baseUrl + "/ocene/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(List.class);
    }
}
