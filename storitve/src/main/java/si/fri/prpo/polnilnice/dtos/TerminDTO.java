package si.fri.prpo.polnilnice.dtos;

import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import java.time.LocalDateTime;

public class TerminDTO {

    private Long id;
    private Uporabnik uporabnik;
    private Polnilnica polnilnica;
    private LocalDateTime zacetek_termina;
    private LocalDateTime konec_termina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Polnilnica getPolnilnica() {
        return polnilnica;
    }

    public void setPolnilnica(Polnilnica polnilnica) {
        this.polnilnica = polnilnica;
    }

    public LocalDateTime getZacetek_termina() {
        return zacetek_termina;
    }

    public void setZacetek_termina(LocalDateTime zacetek_termina) {
        this.zacetek_termina = zacetek_termina;
    }

    public LocalDateTime getKonec_termina() {
        return konec_termina;
    }

    public void setKonec_termina(LocalDateTime konec_termina) {
        this.konec_termina = konec_termina;
    }
}
