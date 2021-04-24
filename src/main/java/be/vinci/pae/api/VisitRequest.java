package be.vinci.pae.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.Date;
import org.glassfish.jersey.server.ContainerRequest;
import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestFactory;
import be.vinci.pae.domain.visitrequest.VisitRequestUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("/visit")
public class VisitRequest {

  @Inject
  VisitRequestFactory visitRequestFactory;

  @Inject
  VisitRequestUCC visitRequestUcc;

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
   * Receive file from the frontend.
   * 
   * @param enabled FormData
   * @param uploadedInputStream FormDataFile
   * @param fileDetail Details of the FormDataFile
   * @return Status code
   */
  /*
   * @POST
   * 
   * @Path("add") // Your Path or URL to call this service
   * 
   * @Consumes(MediaType.MULTIPART_FORM_DATA) public Response
   * uploadFile(@DefaultValue("true") @FormDataParam("enabled") boolean enabled,
   * 
   * @FormDataParam("file") InputStream uploadedInputStream,
   * 
   * @FormDataParam("file") FormDataContentDisposition fileDetail,
   * 
   * @FormDataParam("data") JsonNode jsonPart) {
   * 
   * // System.out.println("Uploaded Input Stream : " + uploadedInputStream); //
   * System.out.println("File detail : " + fileDetail); System.out.println("JSON : " + jsonPart); //
   * System.out.println("Uploaded Input Stream : " + uploadedInputStream); //
   * System.out.println("File detail : " + fileDetail);
   * 
   * 
   * 
   * // Map<String, String> mapa = jsonPart.getParameters(); // VisitRequestDTO visitRequest =
   * jsonPart.getValueAs(VisitRequestDTO.class);
   * 
   * System.out.println("VISITREQUEST : " + jsonPart);
   * 
   * // Your local disk path where you want to store the file String uploadedFileLocation =
   * ".\\images\\" /* + fileDetail.getFileName()
   */;
  /*
   * System.out.println(uploadedFileLocation); // save it File objFile = new
   * File(uploadedFileLocation); if (objFile.exists()) { objFile.delete();
   * 
   * }
   * 
   * saveToFile(uploadedInputStream, uploadedFileLocation);
   */
  /*
   * 
   * String output = "File uploaded via Jersey based RESTFul Webservice to: " +
   * uploadedFileLocation;
   * 
   * return Response.status(200).entity(output).build();
   * 
   * }
   */

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
