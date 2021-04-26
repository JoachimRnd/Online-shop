package be.vinci.pae.domain.visitrequest;

import java.io.InputStream;
import java.util.List;
import be.vinci.pae.domain.user.UserDTO;

public interface VisitRequestUCC {

  VisitRequestDTO addVisitRequest(VisitRequestDTO newVisitRequest, UserDTO user,
      List<InputStream> inputStreamList);

}
