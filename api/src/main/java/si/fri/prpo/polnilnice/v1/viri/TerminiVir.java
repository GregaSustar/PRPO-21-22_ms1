package si.fri.prpo.polnilnice.v1.viri;

import si.fri.prpo.polnilnice.entitete.Termin;
import si.fri.prpo.polnilnice.zrna.TerminiZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("termini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TerminiVir {

    @Inject
    private TerminiZrno terminiZrno;

    @GET
    public Response vrniTermine(){
        ArrayList<Termin> termini = (ArrayList<Termin>) terminiZrno.getTermini();
        return Response
                .status(Response.Status.OK)
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
    public Response vstaviTermin(Termin t) {
        return Response
                .status(Response.Status.OK)
                .entity(terminiZrno.createTermin(t))
                .build();
    }

    @Path("/{id}")
    @DELETE
    public Response izbrisiTermin(@PathParam(value = "id") Long id) {
        return Response
                .status(Response.Status.OK)
                .entity(terminiZrno.deleteTermin(id))
                .build();
    }
}
