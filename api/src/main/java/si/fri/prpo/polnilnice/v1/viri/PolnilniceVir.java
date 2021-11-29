package si.fri.prpo.polnilnice.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.polnilnice.dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.zrna.PolnilniceZrno;
import si.fri.prpo.polnilnice.zrna.UpravljanjePolnilnicZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("polnilnice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PolnilniceVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @Inject
    private UpravljanjePolnilnicZrno upravljanjePolnilnicZrno;

    @GET
    public Response vrniPolnilnice(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Polnilnica> polnilnice = polnilniceZrno.getPolnilnice(query);
        Long polnilniceCount = polnilniceZrno.getPolnilniceCount(query);
        return Response
                .status(Response.Status.OK)
                .header("X-Total-Count", polnilniceCount)
                .entity(polnilnice)
                .build();
    }

    @Path("/{id}")
    @GET
    public Response vrniPolnilnico(@PathParam(value = "id") Long id) {
        Polnilnica polnilnica = polnilniceZrno.getPolnilnica(id);
        return Response
                .status(Response.Status.OK)
                .entity(polnilnica)
                .build();
    }

    @Path("/{id}")
    @PUT
    /*public Response posodobiPolnilnico(@PathParam(value = "id") Long id, Polnilnica p) {
        return Response
                .status(Response.Status.OK)
                .entity(polnilniceZrno.updatePolnilnica(id, p))
                .build();
    }*/
    public Response posodobiPolnilnico(@PathParam(value = "id") Long id, PolnilnicaDTO p) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjePolnilnicZrno.posodobiOsnovnePodatkePolnilnice(id, p))
                .build();
    }

    @POST
    /*public Response vstaviPolnilnico(Polnilnica p) {
        return Response
                .status(Response.Status.OK)
                .entity(polnilniceZrno.createPolnilnica(p))
                .build();
    }*/
    public Response vstaviPolnilnico(PolnilnicaDTO p) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjePolnilnicZrno.ustvariPolnilnico(p))
                .build();
    }

    @Path("/{id}")
    @DELETE
    /*public Response izbrisiPolnilnico(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(polnilniceZrno.deletePolnilnica(id))
                .build();
    }*/
    public Response izbrisiPolnilnico(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjePolnilnicZrno.izbrisiPolnilnico(id))
                .build();
    }
}
