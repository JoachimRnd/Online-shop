package be.vinci.pae.api;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.filters.AuthorizeAdmin;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.FurnitureDTO;
import be.vinci.pae.domain.FurnitureFactory;
import be.vinci.pae.domain.FurnitureUCC;
import be.vinci.pae.domain.OptionDTO;
import be.vinci.pae.domain.OptionFactory;
import be.vinci.pae.domain.OptionUCC;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.ValueLiaison;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.glassfish.jersey.server.ContainerRequest;

@Singleton
@Path("/furniture")
public class Furniture {

  // TODO
  @Inject
  private FurnitureUCC furnitureUCC;

  @Inject
  private OptionUCC optionUCC;

  @Inject
  private OptionFactory optionFactory;

  @Inject
  private FurnitureFactory furnitureFactory;

  /**
   * modify the condition of a furniture.
   *
   * @param json json of the user
   * @return http response
   */
  @POST
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public Response modifyStatus(JsonNode json) {
    Response response;
    if (!json.hasNonNull("id") || !json.hasNonNull("condition") || json.get("id").asText().isEmpty()
        || json.get("condition").asText().isEmpty()) {
      return Response.status(Status.UNAUTHORIZED).entity("Veuillez remplir les champs")
          .type(MediaType.TEXT_PLAIN).build();
    }
    if (json.get("condition").asText().equals("mise en magasin")) {
      if (furnitureUCC.modifyDepositDate((FurnitureDTO) json.get("id"),
          json.get("status").asText()) != null) {
        response = Response.ok().build();
      } else {
        response = Response.serverError().build();
      }
    } else if (json.get("condition").asText().equals("mise en vente")) {
      if (furnitureUCC.modifySellingDate((FurnitureDTO) json.get("id"),
          json.get("status").asText()) != null) {
        response = Response.ok().build();
      } else {
        response = Response.serverError().build();
      }
    } else if (json.get("condition").asText().equals("mise en atelier")) {
      if (furnitureUCC.modifyWorkshopDate((FurnitureDTO) json.get("id"),
          json.get("status").asText()) != null) {
        response = Response.ok().build();
      } else {
        response = Response.serverError().build();
      }
    } else {
      response = Response.status(Status.UNAUTHORIZED).entity("le status entre n'est pas correct")
          .type(MediaType.TEXT_PLAIN).build();
    }
    return response;
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
    return Json.filterPublicJsonViewAsList(furnitureUCC.getAllFurniture(), FurnitureDTO.class);
  }


  /**
   * List furniture with id.
   *
   * @return FurnitureDTO
   */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public FurnitureDTO getFurniture(@PathParam("id") int id) {
    FurnitureDTO furnitureDTO = furnitureUCC.getFurnitureById(id);
    if (furnitureDTO == null) {
      throw new WebApplicationException("Ressource with id = " + id + " could not be found", null,
          Status.NOT_FOUND);
    }
    return Json.filterPublicJsonView(furnitureDTO, FurnitureDTO.class);
  }

  @POST
  @Path("/{id}/cancelOption")
  @Authorize
  public Response cancelOption(@PathParam("id") int id, @Context ContainerRequest request) {
    UserDTO currentUser = (UserDTO) request.getProperty("user");
    optionUCC.cancelOption(id, currentUser);
    return Response.ok().build();
  }

  @POST
  @Path("/{id}/addOption/{duration}")
  @Authorize
  public Response addOption(@PathParam("id") int id, @Context ContainerRequest request,
      @PathParam("duration") int duration) {
    System.out.println("Ici");
    OptionDTO option = optionFactory.getOption();
    option.setDuration(duration);
    option.setDate(Date.valueOf(LocalDate.now()));
    option.setStatus(ValueLiaison.RUNNING_OPTION_STRING);
    option.setFurniture(furnitureFactory.getFurniture());
    option.getFurniture().setId(id);
    UserDTO currentUser = (UserDTO) request.getProperty("user");
    option.setBuyer(currentUser);
    optionUCC.addOption(option);
    return Response.ok().build();
  }

}
