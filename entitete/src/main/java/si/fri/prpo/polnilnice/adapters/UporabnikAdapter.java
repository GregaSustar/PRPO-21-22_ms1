package si.fri.prpo.polnilnice.adapters;

import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class UporabnikAdapter implements JsonbAdapter<Uporabnik, JsonObject> {

    private static final String url = "http://localhost:8080/";

    @Override
    public JsonObject adaptToJson(Uporabnik u) throws Exception {

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
                .build();
    }

    @Override
    public Uporabnik adaptFromJson(JsonObject json) throws Exception {
        Uporabnik u = new Uporabnik();
        u.setId(Long.parseLong(json.getString("id")));
        u.setIme(json.getString("ime"));
        u.setPriimek(json.getString("priimek"));
        u.setUporabnisko_ime(json.getString("uporabnisko_ime"));
        u.setEmail(json.getString("email"));
        Termin t = new Termin();
        t.setId(Long.parseLong(json.getString("rezervacija")));
        u.setRezervacija(t);
        return u;
    }
}
