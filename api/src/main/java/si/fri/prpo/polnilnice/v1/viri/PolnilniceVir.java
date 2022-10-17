package si.fri.prpo.polnilnice.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.security.annotations.Secure;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.polnilnice.dtos.OcenaDTO;
import si.fri.prpo.polnilnice.dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.zrna.PolnilniceZrno;
import si.fri.prpo.polnilnice.zrna.UpravljanjePolnilnicZrno;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

//@Secure
@ApplicationScoped
@Path("polnilnice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods =  "GET, POST, PUT, DELETE, HEAD, OPTIONS")
public class PolnilniceVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @Inject
    private UpravljanjePolnilnicZrno upravljanjePolnilnicZrno;

    //@PermitAll
    @Operation(summary = "Pridobi podatke o vseh polnilnicah", description = "Vrne podatke o vseh polnilnicah.")
    @APIResponses({
            @APIResponse(description = "Seznam polnilnic",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Polnilnica.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vseh polnilnic v DB")})
    })
    @GET
    public Response vrniPolnilnice(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Polnilnica> polnilnice = polnilniceZrno.getPolnilnice(query);
        Long polnilniceCount = polnilniceZrno.getPolnilniceCount(query);
        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count", polnilniceCount)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity(polnilnice)
                .build();
    }

    //@PermitAll
    @Operation(summary = "Pridobi podatke o polnilnici z ID-jem 'id'", description = "Vrne podatke o polnilnici z podanim ID-jem.")
    @APIResponses({
            @APIResponse(description = "Podatki o polnilnici",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Polnilnica.class))),
            @APIResponse(description = "Polnilnica not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @GET
    public Response vrniPolnilnico(
            @Parameter(
                    description = "identifikator polnilnice, ki jo hočemo pridobiti",
                    required = true) @PathParam(value = "id") Long id) {
        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(id);
        List<OcenaDTO> ocene = polnilniceZrno.pridobiOcene(id);
        PolnilnicaDTO p = new PolnilnicaDTO(polnilnica, ocene);
        return Response
                .status(Response.Status.OK)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity(p)
                .build();
    }

    //@RolesAllowed("user")
    @Operation(summary = "Posodobi osnovne podatke o polnilnici z ID-jem 'id'.",
            description = "Posodobi podatke o polnilnici z podanim ID-jem.")
    @APIResponses({
            @APIResponse(description = "Polnilnica je uspešno posodobljen",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Polnilnica.class))),
            @APIResponse(description = "Polnilnica not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @PUT
    public Response posodobiPolnilnico(
            @Parameter(
                    description = "identifikator polnilnice, ki jo hočemo posodobiti",
                    required = true) @PathParam(value = "id") Long id,
            @RequestBody(
                    description = "DTO objekt z spremenjenimi podatki polnilnice",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Polnilnica.class))) PolnilnicaDTO p) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjePolnilnicZrno.posodobiOsnovnePodatkePolnilnice(id, p))
                .build();
    }

    //@RolesAllowed("user")
    @Operation(summary = "Vstavi novo polnilnico.", description = "Doda novo polnilnico v DB.")
    @APIResponses({
            @APIResponse(description = "Polnilnica je uspešno dodan",
                    responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Polnilnica.class)))
    })
    @POST
    public Response vstaviPolnilnico(
            @RequestBody(
                    description = "DTO objekt z podatki o polnilnice",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Polnilnica.class))) PolnilnicaDTO p) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjePolnilnicZrno.ustvariPolnilnico(p))
                .build();
    }

    //@RolesAllowed("admin")
    @Operation(summary = "Izbrisi polnilnico.", description = "Izbrise polnilnico iz DB.")
    @APIResponses({
            @APIResponse(description = "Polnilnica je uspešno izbrisana",
                    responseCode = "204"),
            @APIResponse(description = "Polnilnica not found",
                    responseCode = "404")
    })
    @Path("/{id}")
    @DELETE
    public Response izbrisiPolnilnico(
            @Parameter(
                    description = "identifikator polnilnice, ki jo hočemo izbrisati",
                    required = true) @PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjePolnilnicZrno.izbrisiPolnilnico(id))
                .build();
    }
}
