package si.fri.prpo.polnilnice.dtos;

import si.fri.prpo.polnilnice.entitete.Polnilnica;

import java.util.List;

public class LokacijaDTO {

    private Long id;
    private Integer postna_st;
    private String drzava;
    private String mesto;
    private String naslov;
    private List<Polnilnica> polnilnice;

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
}
