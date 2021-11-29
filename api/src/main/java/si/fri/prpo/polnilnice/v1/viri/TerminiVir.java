package si.fri.prpo.polnilnice.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
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
@ApplicationScoped
public class TerminiVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private TerminiZrno terminiZrno;

    @Inject
    private UpravljanjeTerminovZrno upravljanjeTerminovZrno;

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

    @Path("/{id}")
    @GET
    public Response vrniUporabnika(@PathParam(value = "id") Long id) {
        Termin termin = terminiZrno.getTermin(id);
        return Response
                .status(Response.Status.OK)
                .entity(termin)
                .build();
    }

    @POST
    /*public Response vstaviTermin(Termin t) {
        return Response
                .status(Response.Status.OK)
                .entity(terminiZrno.createTermin(t))
                .build();
    }*/
    public Response vstaviTermin(TerminDTO t) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeTerminovZrno.ustvariTermin(t))
                .build();
    }

    @Path("/{id}")
    @DELETE
    /*public Response izbrisiTermin(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(terminiZrno.deleteTermin(id))
                .build();
    }*/
    public Response izbrisiTermin(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeTerminovZrno.izbrisiTermin(id))
                .build();
    }
}
