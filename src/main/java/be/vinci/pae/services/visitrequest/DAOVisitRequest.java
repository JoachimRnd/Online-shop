package be.vinci.pae.services.visitrequest;

import be.vinci.pae.domain.visitrequest.VisitRequestDTO;
import java.sql.Timestamp;
import java.util.List;

public interface DAOVisitRequest {

  int addVisitRequest(VisitRequestDTO visitRequest);

  VisitRequestDTO selectVisitRequestById(int id);

  List<VisitRequestDTO> getAllVisitsOpenned();

  boolean cancelVisitRequest(int id, String cancellationReason);

  boolean chooseDateForVisit(int id, Timestamp chosenDateTime);
}
