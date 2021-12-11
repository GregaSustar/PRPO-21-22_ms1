package si.fri.prpo.polnilnice.dtos;

import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import java.time.LocalTime;
import java.util.List;

public class PolnilnicaDTO {

    private Long id;
    private String ime;
    private Lokacija lokacija;
    private LocalTime cas_odprtja;
    private LocalTime cas_zaprtja;
    private Uporabnik lastnik;
    private List<Termin> termini;
    private Double cena_polnjenja;
    private Double moc_v_kW;
    private Polnilnica.Tok vrsta_toka;

    private List<OcenaDTO> ocene;

    public PolnilnicaDTO(Polnilnica p, List<OcenaDTO> ocene) {
        this.id = p.getId();
        this.ime = p.getIme();
        this.lokacija = p.getLokacija();
        this.cas_odprtja = p.getCas_odprtja();
        this.cas_zaprtja = p.getCas_zaprtja();
        this.lastnik = p.getLastnik();
        this.termini = p.getTermini();
        this.cena_polnjenja = p.getCena_polnjenja();
        this.moc_v_kW = p.getMoc_v_kW();
        this.vrsta_toka = p.getVrsta_toka();
        this.ocene = ocene;
    }

    public List<OcenaDTO> getOcene() {
        return ocene;
    }

    public void setOcene(List<OcenaDTO> ocene) {
        this.ocene = ocene;
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

    public Polnilnica.Tok getVrsta_toka() {
        return vrsta_toka;
    }

    public void setVrsta_toka(Polnilnica.Tok vrsta_toka) {
        this.vrsta_toka = vrsta_toka;
    }
}
