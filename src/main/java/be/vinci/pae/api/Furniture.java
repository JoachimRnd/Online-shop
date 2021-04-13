package be.vinci.pae.api;

import java.time.Instant;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;
import com.fasterxml.jackson.databind.JsonNode;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.filters.AuthorizeAdmin;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.furniture.FurnitureDTO;
import be.vinci.pae.domain.furniture.FurnitureUCC;
import be.vinci.pae.domain.option.OptionDTO;
import be.vinci.pae.domain.option.OptionFactory;
import be.vinci.pae.domain.option.OptionUCC;
import be.vinci.pae.domain.type.TypeDTO;
import be.vinci.pae.domain.type.TypeUCC;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/furniture")
public class Furniture {

  @Inject
  private FurnitureUCC furnitureUCC;

  @Inject
  private OptionUCC optionUCC;

  @Inject
  private OptionFactory optionFactory;

  @Inject
  private TypeUCC typeUCC;

  /**
   * modify the condition of a furniture.
   *
   * @param json json of the user
   * @return http response
   */
  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public Response modifyFurniture(@PathParam("id") int id, JsonNode json) {
    boolean noError = true;
    boolean empty = true;

    System.out.println("Modify");
    if (json.hasNonNull("condition") && !json.get("condition").asText().isEmpty()
        && json.hasNonNull("sellingPrice")) {
      noError = noError && furnitureUCC.modifyCondition(id,
          FurnitureCondition.valueOf(json.get("condition").asText()),
          json.get("sellingPrice").asDouble()); // TODO change in frontend price by sellingPrice
      empty = false;
    }
    if (json.hasNonNull("type")) {
      System.out.println("type");
      noError = noError && furnitureUCC.modifyType(id, json.get("type").asInt());
      empty = false;
    }
    if (json.hasNonNull("purchasePrice")) {
      System.out.println("purchasePrice");
      noError =
          noError && furnitureUCC.modifyPurchasePrice(id, json.get("purchasePrice").asDouble());
      empty = false;
    }
    if (json.hasNonNull("description") && !json.get("description").asText().isEmpty()) {
      System.out.println("description");
      noError = noError && furnitureUCC.modifyDescription(id, json.get("description").asText());
      empty = false;
    }
    if (json.hasNonNull("buyerEmail") && !json.get("buyerEmail").asText().isEmpty()) {
      noError = noError && furnitureUCC.modifyBuyerEmail(id, json.get("buyerEmail").asText());
      empty = false;
    }
    if (json.hasNonNull("withdrawalDate") && !json.get("withdrawalDate").asText().isEmpty()) {
      noError = noError && furnitureUCC.modifyWithdrawalDate(id,
          Instant.parse(json.get("withdrawalDate").asText())); // Use localDate plutot que instant
                                                               // (pas besoin heures minutes et
                                                               // secondes)
      empty = false;
    }

    if (empty) {
      return Response.status(Status.UNAUTHORIZED).entity("Veuillez remplir les champs")
          .type(MediaType.TEXT_PLAIN).build();
    }



    // selling price pouvoir le changer sans changer l'état?

    if (noError) {
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
  public List<FurnitureDTO> listAllFurniture() {
    return Json.filterPublicJsonViewAsList(furnitureUCC.getFurnitureUsers(), FurnitureDTO.class);
  }

  /**
   * List all furniture types.
   *
   * @return List of TypeDTO
   */
  @GET
  @Path("allFurnitureTypes")
  @Produces(MediaType.APPLICATION_JSON)
  public List<TypeDTO> listAllFurnitureTypes() {
    return Json.filterPublicJsonViewAsList(typeUCC.getFurnitureTypes(), TypeDTO.class);
  }

  /**
   * List furniture with id.
   *
   * @return FurnitureDTO
   */
  @GET
  @Path("/{idFurniture}")
  @Produces(MediaType.APPLICATION_JSON)
  public FurnitureDTO getFurniture(@PathParam("idFurniture") int idFurniture) {
    FurnitureDTO furnitureDTO = furnitureUCC.getFurnitureById(idFurniture);
    if (furnitureDTO == null) {
      throw new WebApplicationException(
          "Ressource with id = " + idFurniture + " could not be found", null, Status.NOT_FOUND);
    }
    return Json.filterPublicJsonView(furnitureDTO, FurnitureDTO.class);
  }

  /**
   * Cancel the option on the furniture with id.
   *
   * @return Response
   */
  @PUT
  @Path("/{idFurniture}/option")
  @Authorize
  public Response cancelOption(@PathParam("idFurniture") int idFurniture,
      @Context ContainerRequest request) {
    UserDTO currentUser = (UserDTO) request.getProperty("user");
    optionUCC.cancelOption(idFurniture, currentUser);
    return Response.ok().build();
  }

  /**
   * Add an option on the furniture with id.
   *
   * @return Response
   */
  @POST
  @Path("/{idFurniture}/option")
  @Consumes(MediaType.APPLICATION_JSON)
  @Authorize
  public Response addOption(@PathParam("idFurniture") int idFurniture,
      @Context ContainerRequest request, JsonNode json) {
    if (!json.has("duration")) {
      return Response.status(Status.UNAUTHORIZED).entity("Veuillez remplir les champs")
          .type(MediaType.TEXT_PLAIN).build();
    }
    optionUCC.addOption(idFurniture, json.get("duration").asInt(),
        (UserDTO) request.getProperty("user"));
    return Response.ok().build();
  }

  /**
   * Get the last option on the furniture with id.
   *
   * @return OptionDTO
   */
  @GET
  @Path("/{idFurniture}/option")
  @Produces(MediaType.APPLICATION_JSON)
  public OptionDTO getOption(@PathParam("idFurniture") int idFurniture) {
    OptionDTO option = optionUCC.getLastOptionOfFurniture(idFurniture);
    if (option == null) {
      option = optionFactory.getOption();
    }
    return Json.filterPublicJsonView(option, OptionDTO.class);
  }

  /**
   * List sales furniture.
   *
   * @return List of FurnitureDTO
   */
  @GET
  @Path("sales")
  @Produces(MediaType.APPLICATION_JSON)
  public List<FurnitureDTO> listSalesFurniture() {
    return Json.filterPublicJsonViewAsList(furnitureUCC.getSalesFurnitureAdmin(),
        FurnitureDTO.class);
  }


}
