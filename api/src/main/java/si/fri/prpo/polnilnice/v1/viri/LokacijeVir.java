package si.fri.prpo.polnilnice.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.polnilnice.dtos.LokacijaDTO;
import si.fri.prpo.polnilnice.entitete.Lokacija;
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
@ApplicationScoped
public class LokacijeVir {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private LokacijeZrno lokacijeZrno;

    @Inject
    private UpravljanjeLokacijZrno upravljanjeLokacijZrno;

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

    @Path("/{id}")
    @GET
    public Response vrniLokacijo(@PathParam(value = "id") Long id) {
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

    @POST
    /*public Response vstaviLokacijo(Lokacija l) {
        return Response
                .status(Response.Status.OK)
                .entity(lokacijeZrno.createLokacija(l))
                .build();
    }*/
    public Response vstaviLokacijo(LokacijaDTO l) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeLokacijZrno.ustvariLokacijo(l))
                .build();
    }


    @Path("/{id}")
    @DELETE
    /*public Response izbrisiLokacijo(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(lokacijeZrno.deleteLokacija(id))
                .build();
    }*/
    public Response izbrisiLokacijo(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(upravljanjeLokacijZrno.izbrisiLokacijo(id))
                .build();
    }
}
