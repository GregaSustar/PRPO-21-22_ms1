package si.fri.prpo.polnilnice.entitete;

import javax.persistence.*;
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
                        query = "SELECT t FROM termin t WHERE t.zacetek_termina > :zac_termina ORDER BY t.zacetek_termina"),
                @NamedQuery(name = "Termin.getByID",
                            query = "SELECT t FROM termin t WHERE t.id = :id"),
                // UPDATE Termin
                @NamedQuery(name = "Termin.updateTermin",
                        query = "UPDATE termin t SET t.uporabnik = :uporabnik, " +
                                "t.polnilnica = :polnilnica, " +
                                "t.zacetek_termina = :zac_termina, " +
                                "t.konec_termina = :kon_termina " +
                                "WHERE t.id = :id"),
                // DELETE Termin
                @NamedQuery(name = "Termin.deleteTermin",
                        query = "DELETE FROM termin t WHERE t.id = :id")
        })
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "uporabnik_id")
    private Uporabnik uporabnik;

    @ManyToOne
    @JoinColumn(name = "polnilnica_id")
    private Polnilnica polnilnica;

    private LocalDateTime zacetek_termina;

    private LocalDateTime konec_termina;

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
}
