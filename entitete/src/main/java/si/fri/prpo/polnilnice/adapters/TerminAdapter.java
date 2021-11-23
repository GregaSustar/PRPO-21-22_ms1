package si.fri.prpo.polnilnice.adapters;

import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.entitete.Uporabnik;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class TerminAdapter implements JsonbAdapter<Termin, JsonObject> {

    private static final String url = "http://localhost:8080/";

    @Override
    public JsonObject adaptToJson(Termin t) throws Exception {

        JsonObject jsonUpr = Json.createObjectBuilder()
                .add("id", t.getUporabnik().getId())
                .add("link", url + "uporabniki/" + t.getUporabnik().getId())
                .build();

        JsonObject jsonPol = Json.createObjectBuilder()
                .add("id", t.getPolnilnica().getId())
                .add("link", url + "polnilnice/" + t.getPolnilnica().getId())
                .build();

        return Json.createObjectBuilder()
                .add("id", t.getId())
                .add("uporabnik", jsonUpr)
                .add("polnilnica", jsonPol)
                .add("zacetek_termina", t.getZacetek_termina().toString())
                .add("konec_termina", t.getKonec_termina().toString())
                .build();
    }

    @Override
    public Termin adaptFromJson(JsonObject json) throws Exception {
        Uporabnik u = new Uporabnik();
        Polnilnica p = new Polnilnica();

        return null;
    }
}
