package si.fri.prpo.polnilnice.zrna;

import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class UporabnikiZrno {

    @PersistenceContext(unitName = "polnilnice-jpa")
    private EntityManager em;

    public List<Uporabnik> getUporabniki() {

        Query q = em.createNamedQuery("Uporabnik.getAll");
        return q.getResultList();
    }

}
