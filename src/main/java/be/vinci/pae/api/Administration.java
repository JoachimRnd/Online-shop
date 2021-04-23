package be.vinci.pae.api;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import com.fasterxml.jackson.databind.JsonNode;
import be.vinci.pae.api.filters.AuthorizeAdmin;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.api.utils.Upload;
import be.vinci.pae.domain.furniture.FurnitureDTO;
import be.vinci.pae.domain.furniture.FurnitureUCC;
import be.vinci.pae.domain.option.OptionUCC;
import be.vinci.pae.domain.picture.PictureDTO;
import be.vinci.pae.domain.picture.PictureFactory;
import be.vinci.pae.domain.picture.PictureUCC;
import be.vinci.pae.domain.type.TypeUCC;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.user.UserUCC;
import be.vinci.pae.utils.ValueLink;
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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

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
  private PictureFactory pictureFactory;

  @Inject
  private PictureUCC pictureUcc;

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

  /**
   * List furniture with id.
   *
   * @return FurnitureDTO
   */
  @GET
  @Path("/{idFurniture}")
  @Produces(MediaType.APPLICATION_JSON)
  @AuthorizeAdmin
  public FurnitureDTO getFurniture(@PathParam("idFurniture") int idFurniture) {
    FurnitureDTO furnitureDTO = furnitureUCC.getFurnitureById(idFurniture);
    if (furnitureDTO == null) {
      throw new WebApplicationException(
          "Ressource with id = " + idFurniture + " could not be found", null, Status.NOT_FOUND);
    }
    return Json.filterAdminJsonView(furnitureDTO, FurnitureDTO.class);
  }

  /**
   * Receive file from the frontend.
   * 
   * @param enabled FormData
   * @param uploadedInputStream FormDataFile
   * @param fileDetail Details of the FormDataFile
   * @return Status code
   */
  @POST
  @Path("image") // Your Path or URL to call this service
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @AuthorizeAdmin
  public Response uploadFile(@DefaultValue("true") @FormDataParam("enabled") boolean enabled,
      @FormDataParam("furnitureID") int furnitureId,
      @FormDataParam("file") InputStream uploadedInputStream,
      @FormDataParam("file") FormDataContentDisposition fileDetail) {

    System.out.println(furnitureId);
    System.out.println(fileDetail.getFileName());
    // Your local disk path where you want to store the file
    String uploadedFileLocation = ".\\images\\" + fileDetail.getFileName();
    System.out.println(uploadedFileLocation);
    // save it
    File objFile = new File(uploadedFileLocation);
    if (objFile.exists()) {
      objFile.delete();
    }

    PictureDTO picture = pictureFactory.getPicture();
    picture.setAScrollingPicture(false);
    picture.setName(fileDetail.getFileName());
    picture.setVisibleForEveryone(false);
    System.out.println("Admin " + picture.getName());
    picture = this.pictureUcc.addPicture(furnitureId, picture);

    Upload.saveToFile(uploadedInputStream, uploadedFileLocation);

    String output = "File uploaded via Jersey based RESTFul Webservice to: " + uploadedFileLocation;

    return Response.status(200).entity(output).build();

  }

}
