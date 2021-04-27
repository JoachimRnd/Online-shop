package be.vinci.pae.services.visitrequest;

import be.vinci.pae.domain.visitrequest.VisitRequestDTO;

public interface DAOVisitRequest {

  VisitRequestDTO selectVisitRequestById(int id);

}
