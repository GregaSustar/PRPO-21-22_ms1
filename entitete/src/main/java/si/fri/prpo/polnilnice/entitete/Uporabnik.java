package si.fri.prpo.polnilnice.entitete;

import javax.persistence.*;
import java.util.ArrayList;

@Entity(name = "uporabnik")
@NamedQueries(value =
        {
                @NamedQuery(name = "Uporabnik.getAll",
                            query = "SELECT u FROM uporabnik u"),
                @NamedQuery(name = "Uporabnik.getAllWithTermin",
                        query = "SELECT u FROM uporabnik u WHERE u.rezervacija IS NOT NULL"),
                @NamedQuery(name = "Uporabnik.getAllLastniki",
                        query = "SELECT u FROM uporabnik u WHERE u.polnilnice IS NOT EMPTY"),
                @NamedQuery(name = "Uporabnik.findByUporabniskoIme",
                        query = "SELECT u FROM uporabnik u WHERE u.uporabnisko_ime = ?1")
        })
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private String priimek;

    private String uporabnisko_ime;

    @OneToMany
    @JoinColumn(name = "polnilnice")
    private ArrayList<Polnilnica> polnilnice;

    @OneToOne
    @JoinColumn(name = "termin_id")
    private Termin rezervacija;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public ArrayList<Polnilnica> getPolnilnice() {
        return polnilnice;
    }

    public void setPolnilnice(ArrayList<Polnilnica> polnilnice) {
        this.polnilnice = polnilnice;
    }

    public Termin getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Termin rezervacija) {
        this.rezervacija = rezervacija;
    }
}
