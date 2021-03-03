package be.vinci.pae.api;

import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/auths")
public class Authentication {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private DAOUser daoUser;

  @Inject
  private UserFactory userFactory;

  /**
   * Checking the credentials of a user, create a token, get the user object associate.
   *
   * @param json DOM event user informations
   * @return a response from the responseBuilder
   * @TODO JavaDoc
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("login") || !json.hasNonNull("password")) {
      return Response.status(Status.UNAUTHORIZED).entity("Login and password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }
    String login = json.get("login").asText();
    String password = json.get("password").asText();
    // Try to login
    User user = this.daoUser.getUser(login);
    if (user == null || !user.checkPassword(password)) {
      return Response.status(Status.UNAUTHORIZED).entity("Login or password incorrect")
          .type(MediaType.TEXT_PLAIN).build();
    }
    // Create token
    String token = createToken(user);

    // Build response

    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    User publicUser = Json.filterPublicJsonView(user, User.class);
    ObjectNode node = jsonMapper.createObjectNode().put("token", token).putPOJO("user", publicUser);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();

  }

  /**
   * Checking the credentials of a user, create a token, create a user object.
   *
   * @param json DOM event user informations
   * @return a response from the responseBuilder
   * @TODO JavaDoc
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response register(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("login") || !json.hasNonNull("password")) {
      return Response.status(Status.UNAUTHORIZED).entity("Login and password needed")
          .type(MediaType.TEXT_PLAIN).build();
    }
    String login = json.get("login").asText();
    // Check if user exists
    if (this.daoUser.getUser(login) != null) {
      return Response.status(Status.CONFLICT).entity("This login is already in use")
          .type(MediaType.TEXT_PLAIN).build();
    }
    // create user
    User user = this.userFactory.getUser();
    user.setId(1);
    user.setPseudo(login);
    String password = json.get("password").asText();
    user.setMotDePasse(user.hashPassword(password));
    this.daoUser.addUser(user);

    // Create token
    String token = createToken(user);
    // Build response

    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    User publicUser = Json.filterPublicJsonView(user, User.class);
    ObjectNode node = jsonMapper.createObjectNode().put("token", token).putPOJO("user", publicUser);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();

  }

  /**
   * create a json web token with the secret in the properties file.
   *
   * @param user description
   * @return description
   * @throws WebApplicationException description
   * @TODO Javadoc
   */
  private String createToken(User user) {
    String token;
    try {
      token =
          JWT.create().withIssuer("auth0").withClaim("user", user.getId()).sign(this.jwtAlgorithm);
    } catch (Exception e) {
      throw new WebApplicationException("Unable to create token", e, Status.INTERNAL_SERVER_ERROR);
    }
    return token;
  }

}
