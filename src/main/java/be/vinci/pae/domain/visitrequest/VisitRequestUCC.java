package be.vinci.pae.domain.visitrequest;

import java.io.InputStream;
import java.util.List;
import be.vinci.pae.domain.user.UserDTO;

public interface VisitRequestUCC {

  VisitRequestDTO addVisitRequest(VisitRequestDTO newVisitRequest, UserDTO user,
      List<InputStream> inputStreamList);

  List<VisitRequestDTO> getAllVisitsOpenned();

  VisitRequestDTO getVisitRequestById(int id);

  VisitRequestDTO getVisitRequestByIdForUser(int id, int userId);

  List<VisitRequestDTO> getVisitRequestsByUserId(int userId);

  String modifyVisitRequest(int id, String cancellationReason, String chosenDateTime);

}
