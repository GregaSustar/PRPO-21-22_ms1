package si.fri.prpo.polnilnice.entitete;

import javax.persistence.*;
import java.util.ArrayList;

@Entity(name = "uporabnik")
@NamedQueries(value =
        {
                // READ Uporabnik
                @NamedQuery(name = "Uporabnik.getAll",
                            query = "SELECT u FROM uporabnik u"),
                @NamedQuery(name = "Uporabnik.getAllWithTermin",
                            query = "SELECT u FROM uporabnik u WHERE u.rezervacija IS NOT NULL"),
                @NamedQuery(name = "Uporabnik.getAllLastniki",
                            query = "SELECT u FROM uporabnik u, polnilnica p WHERE p.lastnik = u"),
                @NamedQuery(name = "Uporabnik.getByUporabniskoIme",
                            query = "SELECT u FROM uporabnik u WHERE u.uporabnisko_ime = :upr_ime"),
                @NamedQuery(name = "Uporabnik.getByID",
                            query = "SELECT u FROM uporabnik u WHERE u.id = :id"),
                // UPDATE Uporabnik
                @NamedQuery(name = "Uporabnik.updateUporabnik",
                            query = "UPDATE uporabnik u SET u.ime = :ime, u.priimek = :priimek, u.email = :email, u.uporabnisko_ime = :upr_ime, u.rezervacija = :rez WHERE u.id = :id"),
                // DELETE Uporabnik
                @NamedQuery(name = "Uporabnik.deleteUporabnik",
                            query = "DELETE FROM uporabnik u WHERE u.id = :id")
        })
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;

    private String priimek;

    @Column(unique = true)
    private String uporabnisko_ime;

    private String email;

    @OneToOne
    @JoinColumn(name = "termin_id")
    private Termin rezervacija;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getUporabnisko_ime() {
        return uporabnisko_ime;
    }

    public void setUporabnisko_ime(String uporabnisko_ime) {
        this.uporabnisko_ime = uporabnisko_ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Termin getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Termin rezervacija) {
        this.rezervacija = rezervacija;
    }

    @Override
    public String toString() {
        return "Uporabnik{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", priimek='" + priimek + '\'' +
                ", uporabnisko_ime='" + uporabnisko_ime + '\'' +
                ", email='" + email + '\'' +
                ", rezervacija=" + rezervacija +
                '}';
    }
}
