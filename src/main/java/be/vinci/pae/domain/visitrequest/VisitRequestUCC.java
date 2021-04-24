package be.vinci.pae.domain.visitrequest;

import be.vinci.pae.domain.user.UserDTO;

public interface VisitRequestUCC {

  VisitRequestDTO addVisitRequest(VisitRequestDTO newVisitRequest, UserDTO user);

}
