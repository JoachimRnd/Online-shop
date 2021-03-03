package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.services.DAOUser;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ContainerRequest;

@Singleton
@Path("/users")
public class UserResource {

  @Inject
  private DAOUser daoUser;

  @Inject
  private UserFactory userFactory;

  /**
   * initialize the user.
   *
   * @return a user
   * @TODO JavaDoc
   */
  @POST
  @Path("init")
  @Produces(MediaType.APPLICATION_JSON)
  public User init() {
    User user = this.userFactory.getUser();
    user.setId(1);
    user.setPseudo("james");
    user.setMotDePasse(user.hashPassword("password"));
    this.daoUser.addUser(user);
    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    return Json.filterPublicJsonView(user, User.class);
  }

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
