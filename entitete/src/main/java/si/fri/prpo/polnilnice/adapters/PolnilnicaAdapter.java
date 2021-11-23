package si.fri.prpo.polnilnice.adapters;

import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Termin;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class PolnilnicaAdapter implements JsonbAdapter<Polnilnica, JsonObject> {

    private static final String url = "http://localhost:8080/";

   /* private Long id;
    private String ime;
    private Lokacija lokacija;
    private LocalTime cas_odprtja;
    private LocalTime cas_zaprtja;
    private Uporabnik lastnik;
    private Double cena_polnjenja;
    private List<Termin> termini;
    private Double moc_v_kW;
    private Polnilnica.Tok vrsta_toka;*/

    @Override
    public JsonObject adaptToJson(Polnilnica p) throws Exception {
        /*JsonObject jsonLok = Json.createObjectBuilder()
        JsonObject jsonLas = Json.createObjectBuilder()

        JsonObject jsonTer = Json.createObjectBuilder()
                .add("id", u.getRezervacija().getId())
                .add("link", url + "termini" + u.getRezervacija().getId())
                .build();

        return Json.createObjectBuilder()
                .add("id", u.getId())
                .add("ime", u.getIme())
                .add("priimek", u.getPriimek())
                .add("uporabnisko_ime", u.getUporabnisko_ime())
                .add("email", u.getEmail())
                .add("rezervacija", jsonTer)
                .build();*/

        return null;
    }

    @Override
    public Polnilnica adaptFromJson(JsonObject jsonObject) throws Exception {
        return null;
    }
}
