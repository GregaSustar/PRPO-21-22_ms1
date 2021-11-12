package si.fri.prpo.polnilnice.entitete;

import javax.persistence.*;

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
                // UPDATE Lokacija
                @NamedQuery(name = "Lokacija.updateLokacija",
                        query = "UPDATE lokacija l SET l.drzava = :drzava, " +
                                "l.mesto = :mesto, " +
                                "l.naslov = :naslov, " +
                                "l.postna_st = :postna " +
                                "WHERE l.id = :id"),
                // DELETE Lokacija
                @NamedQuery(name = "Lokacija.deleteLokacija",
                        query = "DELETE FROM lokacija l WHERE l.id = :id")
        })
public class Lokacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer postna_st;

    private String drzava;

    private String mesto;

    private String naslov;


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
}
