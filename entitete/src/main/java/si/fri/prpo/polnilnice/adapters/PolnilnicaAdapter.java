package si.fri.prpo.polnilnice.adapters;

import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.entitete.Termin;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import java.util.ArrayList;
import java.util.List;

public class PolnilnicaAdapter implements JsonbAdapter<Polnilnica, JsonObject> {

    private static final String url = "http://localhost:8080/";

    @Override
    public JsonObject adaptToJson(Polnilnica p) throws Exception {
        JsonObject jsonLok = Json.createObjectBuilder()
                .add("id", p.getLokacija().getId())
                .add("link", url + "lokacije/" + p.getLokacija().getId())
                .build();

        JsonObject jsonLas = Json.createObjectBuilder()
                .add("id", p.getLastnik().getId())
                .add("link", url + "uporabniki/" + p.getLastnik().getId())
                .build();

        List<Termin> termini = (ArrayList<Termin>) p.getTermini();
        JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();
        for(Termin t : termini) {
            JsonObject jsonTer = Json.createObjectBuilder()
                    .add("id", t.getId())
                    .add("link", url + "termini" + t.getId())
                    .build();
            jsonBuilder.add(jsonTer);
        }
        JsonArray jsonTerArr = jsonBuilder.build();

       return Json.createObjectBuilder()
                .add("id", p.getId())
                .add("ime", p.getIme())
                .add("lokacija", jsonLok)
                .add("cas_odprtja", p.getCas_odprtja().toString())
                .add("cas_zaprtja", p.getCas_zaprtja().toString())
                .add("lastnik", jsonLas)
                .add("cena_polnjenja", p.getCena_polnjenja())
                .add("termini", jsonTerArr)
                .add("moc_v_kW", p.getMoc_v_kW())
                .add("vrsta_toka", p.getVrsta_toka().toString())
                .build();
    }

    @Override
    public Polnilnica adaptFromJson(JsonObject jsonObject) throws Exception {
        return null;
    }
}
