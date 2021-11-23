package si.fri.prpo.polnilnice.viri;

import si.fri.prpo.polnilnice.entitete.Uporabnik;
import si.fri.prpo.polnilnice.zrna.UporabnikiZrno;
import si.fri.prpo.polnilnice.zrna.UpravljanjeUporabnikovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("uporabniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UporabnikiVir {

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @GET
    public Response vrniUporabnike(){
        ArrayList<Uporabnik> uporabniki = (ArrayList<Uporabnik>) uporabnikiZrno.getUporabniki();
        return Response.status(Response.Status.OK).entity(uporabniki).build();
    }

    @Path("/{id}")
    @GET
    public Response vrniUporabnika(@PathParam(value = "id") Long id) {
        Uporabnik uporabnik = uporabnikiZrno.getUporabnik(id);
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }
}
