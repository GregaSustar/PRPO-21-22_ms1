package si.fri.prpo.polnilnice.entitete;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;

@Entity(name = "polnilnica")
@NamedQueries(value =
        {
                @NamedQuery(name = "Polnilnica.getAll",
                            query = "SELECT p FROM polnilnica p"),
                @NamedQuery(name = "Polnilnica.getAllInMesto",
                        query = "SELECT p FROM polnilnica p WHERE p.lokacija.mesto = ?1"),
                @NamedQuery(name = "Polnilnica.getAllWithoutTerminiAfterTime",
                        query = "SELECT p FROM polnilnica p, termin t WHERE t.zacetek_termina > ?1"),
                @NamedQuery(name = "Polnilnica.getAllOfLastnik",
                        query = "SELECT p FROM polnilnica p WHERE p.lastnik.id = ?1")
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

    @Transient
    private ArrayList<Termin> termini;

    private Double cena_polnjenja;

    private Double moc_v_kW;

    private String vrsta_toka;

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

    public ArrayList<Termin> getTermini() {
        return termini;
    }

    public void setTermini(ArrayList<Termin> termini) {
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

    public String getVrsta_toka() {
        return vrsta_toka;
    }

    public void setVrsta_toka(String vrsta_toka) {
        this.vrsta_toka = vrsta_toka;
    }
}
