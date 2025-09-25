package pl.miloszgilga.pwp.backend.rest.actuator;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pl.miloszgilga.pwp.backend.rest.actuator.dto.ActuatorResDto;

@Path("/actuator/health")
public class ActuatorController {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ActuatorResDto getHealthState() {
    return new ActuatorResDto("OK");
  }
}
