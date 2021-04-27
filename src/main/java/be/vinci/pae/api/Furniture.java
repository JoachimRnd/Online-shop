package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.filters.AuthorizeAdmin;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.furniture.FurnitureDTO;
import be.vinci.pae.domain.furniture.FurnitureUCC;
import be.vinci.pae.domain.option.OptionDTO;
import be.vinci.pae.domain.option.OptionUCC;
import be.vinci.pae.domain.picture.PictureUCC;
import be.vinci.pae.domain.type.TypeDTO;
import be.vinci.pae.domain.type.TypeUCC;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;

@Singleton
@Path("/furniture")
public class Furniture {

  @Inject
  private FurnitureUCC furnitureUCC;

  @Inject
  private OptionUCC optionUCC;

  @Inject
  private TypeUCC typeUCC;

  @Inject
  private PictureUCC pictureUCC;


  /**
   * List all furniture.
   *
   * @return List of FurnitureDTO
   */
  @GET
  @Path("/{userId}/allFurniture")
  @Produces(MediaType.APPLICATION_JSON)
  public List<FurnitureDTO> listAllFurniture(@PathParam("userId") int userId) {
    return Json.filterPublicJsonViewAsList(furnitureUCC.getFurnitureUsers(userId),
        FurnitureDTO.class);
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
   * Get furniture with id.
   *
   * @return FurnitureDTO
   */
  @GET
  @Path("/{idFurniture}")
  @Produces(MediaType.APPLICATION_JSON)
  public FurnitureDTO getFurniture(@PathParam("idFurniture") int idFurniture) {
    return Json
        .filterPublicJsonView(furnitureUCC.getFurnitureById(idFurniture), FurnitureDTO.class);
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
    return Json
        .filterPublicJsonView(optionUCC.getLastOptionOfFurniture(idFurniture), OptionDTO.class);
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

  /**
   * Get personal furniture with id.
   *
   * @return FurnitureDTO
   */
  @GET
  @Path("/personal/{idFurniture}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public FurnitureDTO getPersonalFurniture(@PathParam("idFurniture") int idFurniture,
      @Context ContainerRequest request) {
    UserDTO currentUser = (UserDTO) request.getProperty("user");
    return Json
        .filterPublicJsonView(furnitureUCC.getPersonalFurnitureById(idFurniture, currentUser),
            FurnitureDTO.class);
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
    return optionUCC.cancelOption(idFurniture, currentUser) ? Response.ok().build()
        : Response.serverError().build();
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
    return optionUCC.addOption(idFurniture, json.get("duration").asInt(),
        (UserDTO) request.getProperty("user")) ? Response.ok().build()
        : Response.serverError().build();
  }

  /**
   * Get the furniture buy by an user.
   *
   * @return List of FurnitureDTO
   */
  @GET
  @Path("/furniturebuyby/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<FurnitureDTO> getFurnitureBuyBy(@PathParam("id") int id) {
    return Json.filterPublicJsonViewAsList(furnitureUCC.getFurnitureBuyBy(id), FurnitureDTO.class);
  }

  /**
   * Get the furniture sell by an user.
   *
   * @return List of FurnitureDTO
   */
  @GET
  @Path("/furnituresellby/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public List<FurnitureDTO> getFurnitureSellBy(@PathParam("id") int id) {
    return Json.filterPublicJsonViewAsList(furnitureUCC.getFurnitureSellBy(id), FurnitureDTO.class);
  }

  //@TODO AuthorizeAdmin => Administration

  /**
   * Get an image.
   *
   * @return Octet Stream
   */
  @GET
  @Path("picture-furniture")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  @AuthorizeAdmin
  public Response getFile() {
    //@TODO Utilité ?
    File file = new File(".\\images\\23.png");
    System.out.println(file);
    return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
        .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
        .build();
  }

  /**
   * Add an favourite picture on the furniture with id.
   *
   * @return Response
   */
  @PUT
  @Path("/{idFurniture}/favourite-picture")
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public Response modifyFavouritePicture(@PathParam("idFurniture") int idFurniture,
      @Context ContainerRequest request, JsonNode json) {
    //@TODO Soucis ICI json.has et json.get différents
    if (!json.has("idPicture")
        || !furnitureUCC.modifyFavouritePicture(idFurniture, json.get("picture_id").asInt())) {
      return Response.status(Status.UNAUTHORIZED).entity("Erreur ajouter photo favorite")
          .type(MediaType.TEXT_PLAIN).build();
    }

    return Response.ok().build();
  }

  /**
   * update scrolling picture or not on the picture with id.
   *
   * @return Response
   */
  @PUT
  @Path("/{idPicture}/scrolling-picture")
  @AuthorizeAdmin
  public Response modifyScrollingPicture(@PathParam("idPicture") int idPicture) {
    return pictureUCC.modifyScrollingPicture(idPicture) ? Response.ok().build()
        : Response.status(Status.UNAUTHORIZED).entity("Erreur ajouter photo défilante")
            .type(MediaType.TEXT_PLAIN).build();
  }

  /**
   * delete picture with id.
   *
   * @return Response
   */
  @DELETE
  @Path("/{idPicture}/picture")
  @AuthorizeAdmin
  public Response deletePicture(@PathParam("idPicture") int idPicture) {
    return pictureUCC.deletePicture(idPicture) ? Response.ok().build()
        : Response.status(Status.UNAUTHORIZED).entity("Erreur retirer l'image")
            .type(MediaType.TEXT_PLAIN).build();
  }

  /**
   * modify furniture information.
   *
   * @param json json of the user
   * @return http response
   */
  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public Response modifyFurniture(@PathParam("id") int id, JsonNode json) {
    //@TODO A REFAIRE !!!!!
    boolean noError = true;
    boolean empty = true;

    System.out.println(json.get("condition"));
    if (json.hasNonNull("condition") && !json.get("condition").asText().isEmpty()) {
      noError = noError && furnitureUCC.modifyCondition(id,
          FurnitureCondition.valueOf(json.get("condition").asText()));
      empty = false;
    }
    if (json.hasNonNull("sellingPrice")) {
      noError = noError && furnitureUCC.modifySellingPrice(id, json.get("sellingPrice").asDouble());
      empty = false;
    }
    if (json.hasNonNull("specialSalePrice")) {
      noError = noError
          && furnitureUCC.modifySpecialSalePrice(id, json.get("specialSalePrice").asDouble());
      empty = false;
    }
    if (json.hasNonNull("type")) {
      noError = noError && furnitureUCC.modifyType(id, json.get("type").asInt());
      empty = false;
    }
    if (json.hasNonNull("purchasePrice")) {
      noError =
          noError && furnitureUCC.modifyPurchasePrice(id, json.get("purchasePrice").asDouble());
      empty = false;
    }
    if (json.hasNonNull("description") && !json.get("description").asText().isEmpty()) {
      noError = noError && furnitureUCC.modifyDescription(id, json.get("description").asText());
      empty = false;
    }
    if (json.hasNonNull("withdrawalDateFromCustomer")
        && !json.get("withdrawalDateFromCustomer").asText().isEmpty()) {
      noError = noError && furnitureUCC.modifyWithdrawalDateFromCustomer(id,
          LocalDate.parse(json.get("withdrawalDateFromCustomer").asText()));
      empty = false;
    }
    if (json.hasNonNull("withdrawalDateToCustomer")
        && !json.get("withdrawalDateToCustomer").asText().isEmpty()) {
      noError = noError && furnitureUCC.modifyWithdrawalDateToCustomer(id,
          LocalDate.parse(json.get("withdrawalDateToCustomer").asText()));
      empty = false;
    }
    if (json.hasNonNull("deliveryDate") && !json.get("deliveryDate").asText().isEmpty()) {
      noError = noError && furnitureUCC.modifyDeliveryDate(id,
          LocalDate.parse(json.get("deliveryDate").asText()));
      empty = false;
    }
    if (json.hasNonNull("buyerEmail") && !json.get("buyerEmail").asText().isEmpty()) {
      noError = noError && furnitureUCC.modifyBuyerEmail(id, json.get("buyerEmail").asText());
      empty = false;
    }

    if (empty) {
      return Response.status(Status.UNAUTHORIZED).entity("Veuillez remplir les champs")
          .type(MediaType.TEXT_PLAIN).build();
    }

    if (noError) {
      return Response.ok().build();
    } else {
      return Response.status(Status.UNAUTHORIZED)
          .entity("Erreur lors de la modification du meuble.").type(MediaType.TEXT_PLAIN).build();
    }
  }
}
