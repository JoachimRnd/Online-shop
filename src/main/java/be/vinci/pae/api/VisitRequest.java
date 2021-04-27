package be.vinci.pae.api;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.ContainerRequest;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.api.utils.Json;
import be.vinci.pae.domain.address.AddressDTO;
import be.vinci.pae.domain.address.AddressUCC;
import be.vinci.pae.domain.furniture.FurnitureDTO;
import be.vinci.pae.domain.picture.PictureDTO;
import be.vinci.pae.domain.picture.PictureFactory;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestFactory;
import be.vinci.pae.domain.visitrequest.VisitRequestUCC;
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

@Singleton
@Path("/visit")
public class VisitRequest {

  @Inject
  VisitRequestFactory visitRequestFactory;

  @Inject
  PictureFactory pictureFactory;

  @Inject
  VisitRequestUCC visitRequestUcc;

  @Inject
  AddressUCC addressUcc;

  /**
   * Add a furniture type.
   *
   * @return http response
   */
  @POST
  @Path("/add")
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize
  public Response addFurnitureType(VisitRequestDTO visitRequest,
      @Context ContainerRequest request) {
    UserDTO currentUser = (UserDTO) request.getProperty("user");
    visitRequest.setRequestDate(Date.from(Instant.now()));
    // visitRequest = this.visitRequestUcc.addVisitRequest(visitRequest, currentUser);

    return Response.ok().build();
  }

  /**
   * List furniture with id.
   *
   * @return FurnitureDTO
   */
  @GET
  @Path("/address/{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  public AddressDTO getAddress(@PathParam("userId") int userId) {
    AddressDTO addressDTO = this.addressUcc.getAddressByUserId(userId);
    if (addressDTO == null) {
      throw new WebApplicationException("Ressource with id = " + userId + " could not be found",
          null, Status.NOT_FOUND);
    }
    return Json.filterPublicJsonView(addressDTO, AddressDTO.class);
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
  @Authorize
  public Response uploadFile(FormDataMultiPart formDataMultiPart,
      @FormDataParam("file") InputStream uploadedInputStream, @Context ContainerRequest request) {
    formDataMultiPart.getField("json").setMediaType(MediaType.APPLICATION_JSON_TYPE);
    VisitRequestDTO visitRequest =
        formDataMultiPart.getField("json").getValueAs(VisitRequestDTO.class);
    if (visitRequest == null) {
      return Response.status(Status.UNAUTHORIZED).entity("La demande de visite est vide")
          .type(MediaType.TEXT_PLAIN).build();
    }
    AddressDTO address = visitRequest.getAddress();
    if (address == null || address.getStreet() == null || address.getStreet().isEmpty()
        || address.getBuildingNumber() == null || address.getBuildingNumber().isEmpty()
        || address.getPostcode() == null || address.getPostcode().isEmpty()
        || address.getCommune() == null || address.getCommune().isEmpty()
        || address.getCountry() == null || address.getCountry().isEmpty()) {
      return Response.status(Status.UNAUTHORIZED)
          .entity("Un ou plusieurs champs sont vides dans l'adresse").type(MediaType.TEXT_PLAIN)
          .build();
    }
    if (visitRequest.getFurnitureList() == null || visitRequest.getFurnitureList().isEmpty()
        || visitRequest.getTimeSlot() == null || visitRequest.getTimeSlot().isEmpty()) {
      return Response.status(Status.UNAUTHORIZED)
          .entity("Il n'y a pas de meuble ou la plage horaire est vide").type(MediaType.TEXT_PLAIN)
          .build();
    }
    for (FurnitureDTO furniture : visitRequest.getFurnitureList()) {
      if (furniture.getDescription() == null || furniture.getDescription().isEmpty()
          || furniture.getType() == null) {
        return Response.status(Status.UNAUTHORIZED)
            .entity("Les informations encodées pour un meuble sont vides")
            .type(MediaType.TEXT_PLAIN).build();
      }
    }
    List<InputStream> inputStreamList = new ArrayList<>();
    int count = 0;
    List<FormDataBodyPart> parts = formDataMultiPart.getFields("file" + count);
    while (parts != null) {
      FurnitureDTO furniture = visitRequest.getFurnitureList().get(count);
      List<PictureDTO> picturesList = new ArrayList<PictureDTO>();

      for (FormDataBodyPart part : parts) {
        PictureDTO picture = pictureFactory.getPicture();
        picture.setName(part.getFormDataContentDisposition().getFileName());
        String pictureType =
            picture.getName().toLowerCase().substring(picture.getName().lastIndexOf('.') + 1);
        if (!pictureType.equals("jpg") && !pictureType.equals("jpeg")
            && !pictureType.equals("png")) {
          return Response.status(Status.UNAUTHORIZED)
              .entity("Le type de la photo doit être jpg, jpeg ou png").type(MediaType.TEXT_PLAIN)
              .build();
        }
        picture.setAScrollingPicture(false);
        picture.setVisibleForEveryone(false);
        picturesList.add(picture);
        InputStream inputStream = part.getEntityAs(InputStream.class);
        inputStreamList.add(inputStream);
      }
      furniture.setPicturesList(picturesList);
      count++;
      parts = formDataMultiPart.getFields("file" + count);
    }
    for (FurnitureDTO furniture : visitRequest.getFurnitureList()) {
      if (furniture.getPicturesList() == null || furniture.getPicturesList().isEmpty()) {
        return Response.status(Status.UNAUTHORIZED)
            .entity("Il manque une photo pour un ou plusieurs meubles").type(MediaType.TEXT_PLAIN)
            .build();
      }
    }

    UserDTO currentUser = (UserDTO) request.getProperty("user");
    visitRequest.setRequestDate(Date.from(Instant.now()));
    visitRequest = this.visitRequestUcc.addVisitRequest(visitRequest, currentUser, inputStreamList);
    if (visitRequest == null) {
      return Response.serverError().build();
    } else {
      return Response.ok().build();
    }
  }
}
