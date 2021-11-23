package si.fri.prpo.polnilnice.v1.viri;

import si.fri.prpo.polnilnice.entitete.Lokacija;
import si.fri.prpo.polnilnice.entitete.Polnilnica;
import si.fri.prpo.polnilnice.zrna.LokacijeZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("lokacije")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class LokacijeVir {

    @Inject
    private LokacijeZrno lokacijeZrno;

    @GET
    public Response vrniLokacije(){
        ArrayList<Lokacija> lokacije = (ArrayList<Lokacija>) lokacijeZrno.getLokacije();
        return Response
                .status(Response.Status.OK)
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

    @Path("/{id}")
    @PUT
    public Response posodobiLokacijo(@PathParam(value = "id") Long id, Lokacija l) {
        return Response
                .status(Response.Status.OK)
                .entity(lokacijeZrno.updateLokacija(id, l))
                .build();
    }

    @POST
    public Response vstaviLokacijo(Lokacija l) {
        return Response
                .status(Response.Status.OK)
                .entity(lokacijeZrno.createLokacija(l))
                .build();
    }

    @Path("/{id}")
    @DELETE
    public Response izbrisiLokacijo(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(lokacijeZrno.deleteLokacija(id))
                .build();
    }
}
