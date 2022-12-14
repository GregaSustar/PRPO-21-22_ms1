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
import si.fri.prpo.polnilnice.dtos.UporabnikDTO;
import si.fri.prpo.polnilnice.entitete.Uporabnik;
import si.fri.prpo.polnilnice.zrna.UporabnikiZrno;
import si.fri.prpo.polnilnice.zrna.UpravljanjeUporabnikovZrno;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods =  "GET, POST, PUT, DELETE, HEAD, OPTIONS")
public class UporabnikiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno;


    @PermitAll
    @Operation(summary = "Pridobi podatke o vseh uporabnikih", description = "Vrne podatke o vseh uporabnikih.")
    @APIResponses({
            @APIResponse(description = "Seznam uporabnikov",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "??tevilo vseh uporabnikov v DB")})
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


    @PermitAll
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
                description = "identifikator uporabnika, ki ga ho??emo pridobiti",
                required = true) @PathParam(value = "id") Long id) {
        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(id);
        return Response
                .status(Response.Status.OK)
                .entity(uporabnik)
                .build();
    }


    @RolesAllowed("user")
    @Operation(summary = "Posodobi osnovne podatke o uporabniku z ID-jem 'id'. Osnovni podatki so ime, priimek, email in uporabnisko ime",
            description = "Posodobi podatke o uporabniku z podanim ID-jem.")
    @APIResponses({
            @APIResponse(description = "Uporabnik je uspe??no posodobljen",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class))),
            @APIResponse(description = "Uporabnik not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @PUT
    public Response posodobiUporabnika(
            @Parameter(
                description = "identifikator uporabnika, ki ga ho??emo posodobiti",
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

    @RolesAllowed("admin")
    @Operation(summary = "Vstavi novega uporabnika.", description = "Doda novega uporabnika v DB.")
    @APIResponses({
            @APIResponse(description = "Uporabnik je uspe??no dodan",
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

    @RolesAllowed("admin")
    @Operation(summary = "Izbrisi uporabnika.", description = "Izbrise uporabnika iz DB.")
    @APIResponses({
            @APIResponse(description = "Uporabnik je uspe??no izbrisan",
                    responseCode = "204"),
            @APIResponse(description = "Uporabnik not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @DELETE
    public Response izbrisiUporabnika(
            @Parameter(
                description = "identifikator uporabnika, ki ga ho??emo izbrisati",
                required = true) @PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeUporabnikovZrno.izbrisiUporabnika(id))
                .build();
    }
}
