package si.fri.prpo.polnilnice.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.polnilnice.dtos.LokacijaDTO;
import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import si.fri.prpo.polnilnice.zrna.LokacijeZrno;
import si.fri.prpo.polnilnice.zrna.UpravljanjeLokacijZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@Path("lokacije")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@ApplicationScoped
public class LokacijeVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private LokacijeZrno lokacijeZrno;

    @Inject
    private UpravljanjeLokacijZrno upravljanjeLokacijZrno;

    @Operation(summary = "Pridobi podatke o vseh lokacijah", description = "Vrne podatke o vseh lokacijah.")
    @APIResponses({
            @APIResponse(description = "Seznam lokacij",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Lokacija.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vseh lokacij v DB")})
    })
    @GET
    public Response vrniLokacije(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Lokacija> lokacije = lokacijeZrno.getLokacije(query);
        Long lokacijeCount = lokacijeZrno.getLokacijeCount(query);
        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count", lokacijeCount)
                .entity(lokacije)
                .build();
    }

    @Operation(summary = "Pridobi podatke o Lokaciji z ID-jem 'id'", description = "Vrne podatke o lokaciji z podanim ID-jem.")
    @APIResponses({
            @APIResponse(description = "Podatki o lokaciji",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Lokacija.class))),
            @APIResponse(description = "Lokacija not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @GET
    public Response vrniLokacijo(
            @Parameter(
                    description = "identifikator lokacije, ki jo hočemo pridobiti",
                    required = true) @PathParam(value = "id") Long id) {
        Lokacija lokacija = lokacijeZrno.getLokacija(id);
        return Response
                .status(Response.Status.OK)
                .entity(lokacija)
                .build();
    }

    // probably ne bom uporabljal tega in bom lokacije samo brisal in kreiral (podobno kot termine)
    @Path("/{id}")
    @PUT
    public Response posodobiLokacijo(@PathParam(value = "id") Long id, Lokacija l) {
        return Response
                .status(Response.Status.OK)
                .entity(lokacijeZrno.updateLokacija(id, l))
                .build();
    }

    @Operation(summary = "Vstavi novo lokacijo.", description = "Doda novo lokacijo v DB.")
    @APIResponses({
            @APIResponse(description = "Lokacija je uspešno dodana",
                    responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Lokacija.class)))
    })
    @POST
    public Response vstaviLokacijo(
            @RequestBody(
                    description = "DTO objekt z podatki o lokaciji",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Lokacija.class))) LokacijaDTO l) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeLokacijZrno.ustvariLokacijo(l))
                .build();
    }


    @Operation(summary = "Izbrisi Lokacijo.", description = "Izbrise Lokacjio iz DB.")
    @APIResponses({
            @APIResponse(description = "Lokacija je uspešno izbrisana",
                    responseCode = "204"),
            @APIResponse(description = "Lokacija not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @DELETE
    public Response izbrisiLokacijo(
            @Parameter(
                    description = "identifikator lokacije, ki jo hočemo izbrisati",
                    required = true) @PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeLokacijZrno.izbrisiLokacijo(id))
                .build();
    }
}
