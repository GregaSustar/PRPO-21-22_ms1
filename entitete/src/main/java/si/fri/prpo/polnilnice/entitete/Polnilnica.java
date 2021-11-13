package si.fri.prpo.polnilnice.entitete;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "polnilnica")
@NamedQueries(value =
        {
                // READ Polnilnica
                @NamedQuery(name = "Polnilnica.getAll",
                            query = "SELECT p FROM polnilnica p"),
                @NamedQuery(name = "Polnilnica.getAllInMesto",
                            query = "SELECT p FROM polnilnica p WHERE p.lokacija.mesto = :mesto"),
                @NamedQuery(name = "Polnilnica.getAllOfLastnik",
                            query = "SELECT p FROM polnilnica p WHERE p.lastnik.id = :id")
        })
public class Polnilnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;

    @ManyToOne
    @JoinColumn(name = "lokacija_id")
    private Lokacija lokacija;

    private LocalTime cas_odprtja;

    private LocalTime cas_zaprtja;

    @ManyToOne
    @JoinColumn(name = "lastnik_id")
    private Uporabnik lastnik;

    //@Transient
    @OneToMany
    @JoinColumn(name = "termini")
    private List<Termin> termini;

    private Double cena_polnjenja;

    private Double moc_v_kW;

    @Enumerated(EnumType.STRING)
    private Tok vrsta_toka;

    protected Polnilnica() {}

    public Polnilnica(String ime, Lokacija lokacija, LocalTime cas_odprtja, LocalTime cas_zaprtja, Uporabnik lastnik, Double cena_polnjenja, Double moc_v_kW, Tok vrsta_toka) {
        this(ime, lokacija, cas_odprtja, cas_zaprtja, lastnik, null, cena_polnjenja, moc_v_kW, vrsta_toka);
    }

    public Polnilnica(String ime, Lokacija lokacija, LocalTime cas_odprtja, LocalTime cas_zaprtja, Uporabnik lastnik, List<Termin> termini, Double cena_polnjenja, Double moc_v_kW, Tok vrsta_toka) {
        this.ime = ime;
        this.lokacija = lokacija;
        this.cas_odprtja = cas_odprtja;
        this.cas_zaprtja = cas_zaprtja;
        this.lastnik = lastnik;
        this.termini = termini;
        this.cena_polnjenja = cena_polnjenja;
        this.moc_v_kW = moc_v_kW;
        this.vrsta_toka = vrsta_toka;
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

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public LocalTime getCas_odprtja() {
        return cas_odprtja;
    }

    public void setCas_odprtja(LocalTime cas_odprtja) {
        this.cas_odprtja = cas_odprtja;
    }

    public LocalTime getCas_zaprtja() {
        return cas_zaprtja;
    }

    public void setCas_zaprtja(LocalTime cas_zaprtja) {
        this.cas_zaprtja = cas_zaprtja;
    }

    public Uporabnik getLastnik() {
        return lastnik;
    }

    public void setLastnik(Uporabnik lastnik) {
        this.lastnik = lastnik;
    }

    public List<Termin> getTermini() {
        return termini;
    }

    public void setTermini(List<Termin> termini) {
        this.termini = termini;
    }

    public Double getCena_polnjenja() {
        return cena_polnjenja;
    }

    public void setCena_polnjenja(Double cena_polnjenja) {
        this.cena_polnjenja = cena_polnjenja;
    }

    public Double getMoc_v_kW() {
        return moc_v_kW;
    }

    public void setMoc_v_kW(Double moc_v_kW) {
        this.moc_v_kW = moc_v_kW;
    }

    public Tok getVrsta_toka() {
        return vrsta_toka;
    }

    public void setVrsta_toka(Tok vrsta_toka) {
        this.vrsta_toka = vrsta_toka;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("P: { ");
        str.append("id: " + getId() + ", ");
        str.append("ime: " + getIme() + ", ");
        str.append("las_id: " + getLastnik().getId() + ", ");
        str.append(getLokacija().toString());
        str.append(" }");
        return str.toString();
    }

    private enum Tok {
        AC, DC
    }
}
