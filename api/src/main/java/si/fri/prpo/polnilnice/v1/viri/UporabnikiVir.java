package si.fri.prpo.polnilnice.v1.viri;

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
import si.fri.prpo.polnilnice.dtos.UporabnikDTO;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import si.fri.prpo.polnilnice.zrna.UporabnikiZrno;
import si.fri.prpo.polnilnice.zrna.UpravljanjeUporabnikovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno;


    @Operation(summary = "Pridobi podatke o vseh uporabnikih", description = "Vrne podatke o vseh uporabnikih.")
    @APIResponses({
            @APIResponse(description = "Seznam uporabnikov",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vseh uporabnikov v DB")})
    })
    @GET
    public Response vrniUporabnike(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Uporabnik> uporabniki = uporabnikiZrno.getUporabniki(query);
        Long uporabnikiCount = uporabnikiZrno.getUporabnikiCount(query);
        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count", uporabnikiCount)
                .entity(uporabniki)
                .build();
    }


    @Operation(summary = "Pridobi podatke o uporabniku z ID-jem 'id'", description = "Vrne podatke o uporabniku z podanim ID-jem.")
    @APIResponses({
            @APIResponse(description = "Podatki o uporabniku",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))),
            @APIResponse(description = "Uporabnik not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @GET
    public Response vrniUporabnika(
            @Parameter(
                description = "identifikator uporabnika, ki ga hočemo pridobiti",
                required = true) @PathParam(value = "id") Long id) {
        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(id);
        return Response
                .status(Response.Status.OK)
                .entity(uporabnik)
                .build();
    }


    @Operation(summary = "Posodobi osnovne podatke o uporabniku z ID-jem 'id'. Osnovni podatki so ime, priimek, email in uporabnisko ime",
            description = "Posodobi podatke o uporabniku z podanim ID-jem.")
    @APIResponses({
            @APIResponse(description = "Uporabnik je uspešno posodobljen",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))),
            @APIResponse(description = "Uporabnik not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @PUT
    public Response posodobiUporabnika(
            @Parameter(
                description = "identifikator uporabnika, ki ga hočemo posodobiti",
                required = true) @PathParam(value = "id") Long id,
            @RequestBody(
                description = "DTO objekt z spremenjenimi podatki uporabnika",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Uporabnik.class))) UporabnikDTO u) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeUporabnikovZrno.posodobiOsnovnePodatkeUporabnika(id, u))
                .build();
    }

    @Operation(summary = "Vstavi novega uporabnika.", description = "Doda novega uporabnika v DB.")
    @APIResponses({
            @APIResponse(description = "Uporabnik je uspešno dodan",
                    responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class)))
    })
    @POST
    public Response vstaviUporabnika(@RequestBody(
            description = "DTO objekt z podatki o uporabniku",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = Uporabnik.class)))
                                                 UporabnikDTO u) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeUporabnikovZrno.ustvariUporabnika(u))
                .build();
    }

    @Operation(summary = "Izbrisi uporabnika.", description = "Izbrise uporabnika iz DB.")
    @APIResponses({
            @APIResponse(description = "Uporabnik je uspešno izbrisan",
                    responseCode = "204"),
            @APIResponse(description = "Uporabnik not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @DELETE
    public Response izbrisiUporabnika(
            @Parameter(
                description = "identifikator uporabnika, ki ga hočemo izbrisati",
                required = true) @PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeUporabnikovZrno.izbrisiUporabnika(id))
                .build();
    }
}
