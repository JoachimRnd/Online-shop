package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.User;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ContainerRequest;

@Singleton
@Path("/users")
public class UserResource {

  /**
   * get the user related to the request.
   *
   * @param request incomming client request
   * @return a user
   * @TODO JavaDoc
   */
  @GET
  @Path("me")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public User getUser(@Context ContainerRequest request) {
    User currentUser = (User) request.getProperty("user");
    return Json.filterPublicJsonView(currentUser, User.class);
  }

}
