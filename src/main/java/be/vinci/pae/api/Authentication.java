package be.vinci.pae.api;

import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.Address;
import be.vinci.pae.domain.AddressFactory;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.domain.UserUCC;
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
import java.time.LocalDateTime;

@Singleton
@Path("/auths")
public class Authentication {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private UserUCC userUCC;

  @Inject
  private UserFactory userFactory;

  @Inject
  private AddressFactory addressFactory;

  /**
   * Checking the credentials of a user, create a token, get the user object associate.
   *
   * @param json DOM event user informations
   * @return a response from the responseBuilder
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response login(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("username") || !json.hasNonNull("password")
        || json.get("username").asText().isEmpty() || json.get("password").asText().isEmpty()) {
      return Response.status(Status.UNAUTHORIZED).entity("Veuillez remplir les champs")
          .type(MediaType.TEXT_PLAIN).build();
    }

    String username = json.get("username").asText();
    String password = json.get("password").asText();

    // Try to login
    UserDTO user = userUCC.login(username, password);

    // Create token
    String token = createToken(user);

    // Build response
    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    UserDTO publicUser = Json.filterPublicJsonView(user, UserDTO.class);
    ObjectNode node = jsonMapper.createObjectNode().put("token", token).putPOJO("user", publicUser);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();

  }

  /**
   * Checking the credentials of a user, create a token, create a user object.
   *
   * @param json DOM event user informations
   * @return a response from the responseBuilder
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response register(JsonNode json) {
    JsonNode jsonAddress = json.get("address");
    // Get and check credentials
    if (!json.hasNonNull("username")
        || json.get("username").asText().isEmpty()
        || !json.hasNonNull("lastname")
        || json.get("lastname").asText().isEmpty()
        || !json.hasNonNull("firstname")
        || json.get("firstname").asText().isEmpty()
        || !json.hasNonNull("email")
        || json.get("email").asText().isEmpty()
        || !json.hasNonNull("password")
        || json.get("password").asText().isEmpty()
        || !json.hasNonNull("address")
        || !jsonAddress.hasNonNull("street")
        || jsonAddress.get("street").asText().isEmpty()
        || !jsonAddress.hasNonNull("buildingnumber")
        || jsonAddress.get("buildingnumber").asText().isEmpty()
        || !jsonAddress.hasNonNull("postcode")
        || jsonAddress.get("postcode").asText().isEmpty()
        || !jsonAddress.hasNonNull("commune")
        || jsonAddress.get("commune").asText().isEmpty()
        || !jsonAddress.hasNonNull("country")
        || jsonAddress.get("country").asText().isEmpty()) {
      return Response.status(Status.UNAUTHORIZED).entity("Veuillez remplir les champs")
          .type(MediaType.TEXT_PLAIN).build();
    }

    UserDTO user = userFactory.getUser();

    user.setUsername(json.get("username").asText());
    user.setFirstName(json.get("firstname").asText());
    user.setLastName(json.get("lastname").asText());
    user.setEmail(json.get("email").asText());
    user.setPassword(json.get("password").asText());
    Address address = addressFactory.getAddress();
    address.setStreet(jsonAddress.get("street").asText());
    address.setBuildingNumber(jsonAddress.get("buildingnumber").asText());
    address.setPostcode(jsonAddress.get("postcode").asText());
    address.setCommune(jsonAddress.get("commune").asText());
    address.setCountry(jsonAddress.get("country").asText());
    if (jsonAddress.hasNonNull("unitnumber")
        && !jsonAddress.get("unitnumber").asText().isEmpty()) {
      address.setUnitNumber(jsonAddress.get("unitnumber").asText());
    }

    user.setAddress(address);
    user.setRegistrationDate(LocalDateTime.now());
    user.setValidRegistration(false);

    //Try to register
    user = userUCC.register(user);

    // Create token
    String token = createToken(user);
    // Build response

    // load the user data from a public JSON view to filter out the private info not
    // to be returned by the API (such as password)
    UserDTO publicUser = Json.filterPublicJsonView(user, UserDTO.class);
    ObjectNode node = jsonMapper.createObjectNode().put("token", token).putPOJO("user", publicUser);
    return Response.ok(node, MediaType.APPLICATION_JSON).build();
  }

  /**
   * create a json web token with the secret in the properties file.
   *
   * @param user description
   * @return description
   * @throws WebApplicationException description
   */
  private String createToken(UserDTO user) {
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
