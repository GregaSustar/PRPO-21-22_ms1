package si.fri.prpo.polnilnice.entitete;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
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
                @NamedQuery(name = "Uporabnik.getByEmail",
                        query = "SELECT u FROM uporabnik u WHERE u.email = :email")
        })
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;

    private String priimek;

    @Column(unique = true)
    private String uporabnisko_ime;

    @Column(unique = true)
    private String email;

    @JsonbTransient
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Termin rezervacija;

    public Uporabnik() {}

    public Uporabnik(String ime, String priimek, String uporabnisko_ime, String email) {
        this.ime = ime;
        this.priimek = priimek;
        this.uporabnisko_ime = uporabnisko_ime;
        this.email = email;
        this.rezervacija = null;
    }

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
        StringBuilder str = new StringBuilder();
        str.append("U: { ");
        str.append("id: " + getId() + ", ");
        str.append("ime: " + getIme() + ", ");
        str.append("priimek: " + getPriimek() + ", ");
        str.append("u_ime: " + getUporabnisko_ime() + ", ");
        str.append("email: " + getEmail() + ", ");
        if(getRezervacija() != null)
            str.append(getRezervacija().toString());
        else
            str.append("null");
        str.append(" }");
        return str.toString();
    }
}
