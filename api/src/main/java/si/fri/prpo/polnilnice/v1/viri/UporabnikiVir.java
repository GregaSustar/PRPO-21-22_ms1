package si.fri.prpo.polnilnice.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
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

    @Path("/{id}")
    @GET
    public Response vrniUporabnika(@PathParam(value = "id") Long id) {
        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(id);
        return Response
                .status(Response.Status.OK)
                .entity(uporabnik)
                .build();
    }

    @Path("/{id}")
    @PUT
    /*public Response posodobiUporabnika(@PathParam(value = "id") Long id, Uporabnik u) {
        return Response
                .status(Response.Status.OK)
                .entity(uporabnikiZrno.updateUporabnik(id, u))
                .build();
    }*/
    public Response posodobiUporabnika(@PathParam(value = "id") Long id, UporabnikDTO u) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeUporabnikovZrno.posodobiOsnovnePodatkeUporabnika(id, u))
                .build();
    }

    @POST
    /*public Response vstaviUporabnika(Uporabnik u) {
        return Response
                .status(Response.Status.OK)
                .entity(uporabnikiZrno.createUporabnik(u))
                .build();
    }*/
    public Response vstaviUporabnika(UporabnikDTO u) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeUporabnikovZrno.ustvariUporabnika(u))
                .build();
    }

    @Path("/{id}")
    @DELETE
    /*public Response izbrisiUporabnika(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(uporabnikiZrno.deleteUporabnik(id))
                .build();
    }*/
    public Response izbrisiUporabnika(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeUporabnikovZrno.izbrisiUporabnika(id))
                .build();
    }
}
