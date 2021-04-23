package be.vinci.pae.api;

import be.vinci.pae.api.filters.AuthorizeAdmin;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.address.AddressUCC;
import be.vinci.pae.domain.furniture.FurnitureDTO;
import be.vinci.pae.domain.furniture.FurnitureUCC;
import be.vinci.pae.domain.option.OptionUCC;
import be.vinci.pae.domain.type.TypeUCC;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.user.UserUCC;
import be.vinci.pae.utils.ValueLink;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

@Singleton
@Path("/admin")
public class Administration {

  @Inject
  private UserUCC userUCC;

  @Inject
  private OptionUCC optionUCC;

  @Inject
  private FurnitureUCC furnitureUCC;

  @Inject
  private TypeUCC typeUCC;

  @Inject
  private AddressUCC addressUCC;

  /**
   * Valid a user.
   *
   * @param json json of the user
   * @return http response
   */
  @PUT
  @Path("validate")
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public Response validateUser(JsonNode json) {
    if (!json.hasNonNull("id") || !json.hasNonNull("type") || json.get("id").asText().isEmpty()
        || json.get("type").asText().isEmpty()) {
      return Response.status(Status.UNAUTHORIZED).entity("Veuillez remplir les champs")
          .type(MediaType.TEXT_PLAIN).build();
    }

    System.out.println("Id : " + json.get("id").asInt());
    System.out.println("Type : " + json.get("type").asText());

    if (userUCC.validateUser(json.get("id").asInt(),
        ValueLink.UserType.valueOf(json.get("type").asText()))) {
      return Response.ok().build();
    } else {
      return Response.serverError().build();
    }

  }

  /**
   * List all furniture.
   *
   * @return List of FurnitureDTO
   */
  @GET
  @Path("allFurniture")
  @Produces(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public List<FurnitureDTO> listAllFurniture() {
    return Json.filterPublicJsonViewAsList(furnitureUCC.getAllFurniture(), FurnitureDTO.class);
  }

  /**
   * List all unvalidated users.
   *
   * @return List of userDTO
   */
  @GET
  @Path("validate")
  @Produces(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public List<UserDTO> listUnvalidatedUser() {
    return Json.filterPublicJsonViewAsList(userUCC.getUnvalidatedUsers(), UserDTO.class);
  }

  @PUT
  @Path("/{id}/cancelOption")
  @AuthorizeAdmin
  public Response cancelOption(@PathParam("id") int id) {
    optionUCC.cancelOptionByAdmin(id);
    return Response.ok().build();
  }


  /**
   * Delete furniture type.
   *
   * @return http response
   */
  @DELETE
  @Path("/type/{id}")
  @AuthorizeAdmin
  public Response deleteFurnitureType(@PathParam("id") int id) {
    if (id == 0) {
      throw new WebApplicationException(Response.status(Status.BAD_REQUEST)
          .entity("Lacks of mandatory id info").type("text/plain").build());
    }
    if (!typeUCC.deleteFurnitureType(id)) {
      return Response.serverError().build();
    } else {
      return Response.ok().build();
    }
  }


  /**
   * Add a furniture type.
   *
   * @return http response
   */
  @POST
  @Path("/type")
  @Produces(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public Response addFurnitureType(JsonNode json) {
    if (!json.hasNonNull("type") || json.get("type").asText().isEmpty()) {
      return Response.status(Status.UNAUTHORIZED).entity("Veuillez remplir les champs")
          .type(MediaType.TEXT_PLAIN).build();
    }
    int type = typeUCC.addFurnitureType(json.get("type").asText());
    if (type == -1) {
      return Response.serverError().build();
    } else {
      return Response.ok(type).build();
    }
  }

  @GET
  @Path("/allusernames")
  @Produces(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public List<String> allUsers() {
    return Json.filterPublicJsonViewAsList(userUCC.getAllUsername(), String.class);
  }

  @GET
  @Path("/alltypesnames")
  @Produces(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public List<String> allTypes() {
    return Json.filterPublicJsonViewAsList(typeUCC.getAllTypeNames(), String.class);
  }

  @GET
  @Path("/allcommunes")
  @Produces(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public List<String> allCommunes() {
    return Json.filterPublicJsonViewAsList(addressUCC.getAllCommunes(), String.class);
  }

  @GET
  @Path("/users")
  @Produces(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public List<UserDTO> usersFiltered(@DefaultValue("") @QueryParam("username") String username,
      @DefaultValue("") @QueryParam("postcode") String postcode,
      @DefaultValue("") @QueryParam("commune") String commune) {
    return Json.filterPublicJsonViewAsList(userUCC.getUsersFiltered(username, postcode, commune),
        UserDTO.class);
  }

  @GET
  @Path("/furnitures")
  @Produces(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public List<FurnitureDTO> furnituresFiltered(@DefaultValue("") @QueryParam("type") String type,
      @DefaultValue("" + Double.MAX_VALUE) @QueryParam("price") double price,
      @DefaultValue("") @QueryParam("username") String username) {
    return Json
        .filterPublicJsonViewAsList(furnitureUCC.getFurnituresFiltered(type, price, username),
            FurnitureDTO.class);
  }

}
