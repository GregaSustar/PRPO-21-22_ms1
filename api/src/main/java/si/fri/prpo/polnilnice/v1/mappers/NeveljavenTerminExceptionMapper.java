package si.fri.prpo.polnilnice.v1.mappers;

import si.fri.prpo.polnilnice.izjeme.NeveljavenTerminIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavenTerminExceptionMapper implements ExceptionMapper<NeveljavenTerminIzjema> {

    @Override
    public Response toResponse(NeveljavenTerminIzjema exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\": \"" + exception.getMessage() + "\"}")
                .build();
    }
}
