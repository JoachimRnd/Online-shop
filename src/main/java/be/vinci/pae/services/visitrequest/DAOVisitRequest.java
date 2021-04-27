package be.vinci.pae.services.visitrequest;

import be.vinci.pae.domain.visitrequest.VisitRequestDTO;

public interface DAOVisitRequest {

  int addVisitRequest(VisitRequestDTO visitRequest);

  VisitRequestDTO selectVisitRequestById(int id);

}
