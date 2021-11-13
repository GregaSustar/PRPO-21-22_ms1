package si.fri.prpo.polnilnice.dtos;

import si.fri.prpo.polnilnice.entitete.Termin;

public class UporabnikDTO {

    private Long id;
    private String ime;
    private String priimek;
    private String uporabnisko_ime;
    private String email;
    private Termin rezervacija;

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
}
