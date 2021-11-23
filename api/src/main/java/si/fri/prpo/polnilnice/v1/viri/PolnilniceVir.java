package si.fri.prpo.polnilnice.v1.viri;

import si.fri.prpo.polnilnice.dtos.PolnilnicaDTO;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.zrna.PolnilniceZrno;
import si.fri.prpo.polnilnice.zrna.UpravljanjePolnilnicZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("polnilnice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PolnilniceVir {

    @Inject
    private PolnilniceZrno polnilniceZrno;

    @Inject
    private UpravljanjePolnilnicZrno upravljanjePolnilnicZrno;

    @GET
    public Response vrniPolnilnice(){
        ArrayList<Polnilnica> polnilnice = (ArrayList<Polnilnica>) polnilniceZrno.getPolnilnice();
        return Response
                .status(Response.Status.OK)
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
