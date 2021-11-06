package si.fri.prpo.polnilnice.entitete;

import javax.persistence.*;

@Entity(name = "lokacija")
@NamedQueries(value =
        {
                @NamedQuery(name = "Lokacija.getAll",
                            query = "SELECT l FROM lokacija l"),
                @NamedQuery(name = "Lokacija.getAllOrderedByDrzava",
                            query = "SELECT l FROM lokacija l ORDER BY l.drzava"),
                @NamedQuery(name = "Lokacija.findByPostnaSt",
                            query = "SELECT l FROM lokacija l WHERE l.postna_st = ?1"),
                @NamedQuery(name = "Lokacija.findByDrzava",
                            query = "SELECT l FROM lokacija l WHERE l.drzava = ?1"),
                @NamedQuery(name = "Lokacija.findByNaslov",
                            query = "SELECT l FROM lokacija l WHERE l.naslov = ?1")
        })
public class Lokacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer postna_st;

    private String drzava;

    private String mesto;

    private String naslov;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
