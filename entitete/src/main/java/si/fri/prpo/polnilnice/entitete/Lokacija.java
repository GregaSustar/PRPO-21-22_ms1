package si.fri.prpo.polnilnice.entitete;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Entity(name = "lokacija")
@NamedQueries(value =
        {
                // READ Lokacija
                @NamedQuery(name = "Lokacija.getAll",
                            query = "SELECT l FROM lokacija l"),
                @NamedQuery(name = "Lokacija.getAllByPostnaSt",
                            query = "SELECT l FROM lokacija l WHERE l.postna_st = :postna_st"),
                @NamedQuery(name = "Lokacija.getAllByDrzava",
                            query = "SELECT l FROM lokacija l WHERE l.drzava = :drzava"),
                @NamedQuery(name = "Lokacija.findByNaslov",
                            query = "SELECT l FROM lokacija l WHERE l.naslov = :naslov"),
        })
public class Lokacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer postna_st;

    private String drzava;

    private String mesto;

    private String naslov;

    //@JsonbTransient
    @JsonbProperty("polnilnice")
    @JoinTable
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Polnilnica> polnilnice;

    public Lokacija() {}

    public Lokacija(Integer postna_st, String drzava, String mesto, String naslov) {
        this(postna_st, drzava, mesto, naslov, null);
    }

    public Lokacija(Integer postna_st, String drzava, String mesto, String naslov, List<Polnilnica> polnilnice) {
        this.postna_st = postna_st;
        this.drzava = drzava;
        this.mesto = mesto;
        this.naslov = naslov;
        this.polnilnice = polnilnice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPostna_st() {
        return postna_st;
    }

    public void setPostna_st(Integer postna_st) {
        this.postna_st = postna_st;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public List<Polnilnica> getPolnilnice() {
        return polnilnice;
    }

    public void setPolnilnice(List<Polnilnica> polnilnice) {
        this.polnilnice = polnilnice;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("L: { ");
        str.append(getNaslov() + ", ");
        str.append(getPostna_st() + ", ");
        str.append(getMesto() + ", ");
        str.append(getDrzava());
        str.append(" }");
        return str.toString();
    }
}
