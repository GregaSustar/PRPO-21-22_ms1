package si.fri.prpo.polnilnice.entitete;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "termin")
@NamedQueries(value =
        {
                // READ Termin
                @NamedQuery(name = "Termin.getAll",
                        query = "SELECT t FROM termin t ORDER BY t.zacetek_termina"),
                @NamedQuery(name = "Termin.getAllForPolnilnica",
                        query = "SELECT t FROM termin t WHERE t.polnilnica.id = :polnilnica ORDER BY t.zacetek_termina"),
                @NamedQuery(name = "Termin.getAllForUporabnik",
                        query = "SELECT t FROM termin t WHERE t.uporabnik.id = :id ORDER BY t.zacetek_termina"),
                @NamedQuery(name = "Termin.getAllBeforeTime",
                        query = "SELECT t FROM termin t WHERE t.konec_termina < :kon_termina ORDER BY t.zacetek_termina"),
                @NamedQuery(name = "Termin.getAllAfterTime",
                        query = "SELECT t FROM termin t WHERE t.zacetek_termina > :zac_termina ORDER BY t.zacetek_termina")
        })
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonbProperty("uporabnik")
    @OneToOne
    private Uporabnik uporabnik;

    @JsonbProperty("polnilnica")
    @ManyToOne
    private Polnilnica polnilnica;

    private LocalDateTime zacetek_termina;

    private LocalDateTime konec_termina;

    public Termin() {}

    public Termin(Uporabnik uporabnik, Polnilnica polnilnica, LocalDateTime zacetek_termina, LocalDateTime konec_termina) {
        this.uporabnik = uporabnik;
        this.polnilnica = polnilnica;
        this.zacetek_termina = zacetek_termina;
        this.konec_termina = konec_termina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Polnilnica getPolnilnica() {
        return polnilnica;
    }

    public void setPolnilnica(Polnilnica polnilnica) {
        this.polnilnica = polnilnica;
    }

    public LocalDateTime getZacetek_termina() {
        return zacetek_termina;
    }

    public void setZacetek_termina(LocalDateTime zacetek_termina) {
        this.zacetek_termina = zacetek_termina;
    }

    public LocalDateTime getKonec_termina() {
        return konec_termina;
    }

    public void setKonec_termina(LocalDateTime konec_termina) {
        this.konec_termina = konec_termina;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("T: { ");
        str.append("id: " + getId() + ", ");
        str.append("p_id: " + getPolnilnica().getId() + ", ");
        str.append("zac_t: " + getZacetek_termina().toString() + ", ");
        str.append("kon_t: " + getKonec_termina().toString());
        str.append(" }");
        return str.toString();
    }
}
