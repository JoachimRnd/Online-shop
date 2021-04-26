package be.vinci.pae.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    visitRequest = this.visitRequestUcc.addVisitRequest(visitRequest, currentUser);

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
      @FormDataParam("file") InputStream uploadedInputStream) {

    formDataMultiPart.getField("json").setMediaType(MediaType.APPLICATION_JSON_TYPE);

    VisitRequestDTO visitRequest =
        formDataMultiPart.getField("json").getValueAs(VisitRequestDTO.class);



    int compteur = 0;
    List<FormDataBodyPart> parts = formDataMultiPart.getFields("file" + compteur);
    while (parts != null) {

      for (FurnitureDTO furniture : visitRequest.getFurnitureList()) {
        List<PictureDTO> picturesList = new ArrayList<PictureDTO>();
        PictureDTO picture = pictureFactory.getPicture();
        System.out.println("Description : " + furniture.getDescription());
      }

      for (FormDataBodyPart part : parts) {
        System.out.println("FileName : " + part.getFormDataContentDisposition().getFileName());
      }
      compteur++;
      parts = formDataMultiPart.getFields("file" + compteur);
    }

    // System.out.println(formDataMultiPart.getField("file0").getFormDataContentDisposition());

    // Your local disk path where you want to store the file

    // String uploadedFileLocation = ".\\images\\"
    // + formDataMultiPart.getField("file").getFormDataContentDisposition().getFileName();
    // System.out.println(uploadedFileLocation); // save it File objFile = new
    // File(uploadedFileLocation);
    // if (objFile.exists()) {
    // objFile.delete();
    //
    // }
    //
    // saveToFile(uploadedInputStream, uploadedFileLocation);
    //
    // String output = "File uploaded via Jersey based RESTFul Webservice to: " +
    // uploadedFileLocation;


    return Response.status(200).build();

  }


  private void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

    try {
      OutputStream out = null;
      int read = 0;
      byte[] bytes = new byte[1024];

      out = new FileOutputStream(new File(uploadedFileLocation));
      while ((read = uploadedInputStream.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
      out.flush();
      out.close();
    } catch (IOException e) {

      e.printStackTrace();
    }

  }
}
