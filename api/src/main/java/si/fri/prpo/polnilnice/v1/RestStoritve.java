package si.fri.prpo.polnilnice.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("v1")
@OpenAPIDefinition(info = @Info(title = "PolnilniceAPI", version = "v1",
        contact = @Contact(email = "gs1121@student.uni-lj.si"),
        license = @License(name="dev"), description = "API za storitve Polnilnice"),
        servers = @Server(url = "http://localhost:8080/"))
public class RestStoritve extends  javax.ws.rs.core.Application{
}
