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
import si.fri.prpo.polnilnice.dtos.TerminDTO;
import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.zrna.TerminiZrno;
import si.fri.prpo.polnilnice.zrna.UpravljanjeTerminovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("termini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@ApplicationScoped
public class TerminiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private TerminiZrno terminiZrno;

    @Inject
    private UpravljanjeTerminovZrno upravljanjeTerminovZrno;

    @Operation(summary = "Pridobi podatke o vseh terminih", description = "Vrne podatke o vseh terminih.")
    @APIResponses({
            @APIResponse(description = "Seznam Terminov",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Termin.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vseh terminov v DB")})
    })
    @GET
    public Response vrniTermine(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Termin> termini = terminiZrno.getTermini(query);
        Long terminiCount = terminiZrno.getTerminiCount(query);
        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count", terminiCount)
                .entity(termini)
                .build();
    }

    @Operation(summary = "Pridobi podatke o terminu z ID-jem 'id'", description = "Vrne podatke o terminu z podanim ID-jem.")
    @APIResponses({
            @APIResponse(description = "Podatki o terminu",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Termin.class))),
            @APIResponse(description = "Termin not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @GET
    public Response vrniTermin(
            @Parameter(
                    description = "identifikator termina, ki ga hočemo pridobiti",
                    required = true) @PathParam(value = "id") Long id) {
        Termin termin = terminiZrno.getTermin(id);
        return Response
                .status(Response.Status.OK)
                .entity(termin)
                .build();
    }

    @Operation(summary = "Vstavi nov Termin.", description = "Doda nov Termin v DB.")
    @APIResponses({
            @APIResponse(description = "Termin je uspešno dodan",
                    responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Termin.class)))
    })
    @POST
    public Response vstaviTermin(
            @RequestBody(
                description = "DTO objekt z podatki o terminu",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Termin.class))) TerminDTO t) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeTerminovZrno.ustvariTermin(t))
                .build();
    }

    @Operation(summary = "Izbrisi termin.", description = "Izbrise termin iz DB.")
    @APIResponses({
            @APIResponse(description = "Termin je uspešno izbrisan",
                    responseCode = "204"),
            @APIResponse(description = "Termin not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @DELETE
    public Response izbrisiTermin(
            @Parameter(
                    description = "identifikator termina, ki ga hočemo izbrisati",
                    required = true) @PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeTerminovZrno.izbrisiTermin(id))
                .build();
    }
}
