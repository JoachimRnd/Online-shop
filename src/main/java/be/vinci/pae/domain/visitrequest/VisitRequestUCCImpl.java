package be.vinci.pae.domain.visitrequest;

import org.apache.commons.text.StringEscapeUtils;
import be.vinci.pae.domain.address.AddressDTO;
import be.vinci.pae.domain.furniture.FurnitureDTO;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.address.DAOAddress;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.services.visitrequest.DAOVisitRequest;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import be.vinci.pae.utils.ValueLink.VisitRequestStatus;
import jakarta.inject.Inject;

public class VisitRequestUCCImpl implements VisitRequestUCC {

  @Inject
  private DalServices dalServices;

  @Inject
  private DAOFurniture daoFurniture;

  @Inject
  private DAOAddress daoAddress;

  @Inject
  private DAOVisitRequest daoVisitRequest;

  @Override
  public VisitRequestDTO addVisitRequest(VisitRequestDTO newVisitRequest, UserDTO user) {
    VisitRequest visitRequest;
    try {
      this.dalServices.startTransaction();
      visitRequest = (VisitRequest) newVisitRequest;

      visitRequest.setCustomer(user);
      AddressDTO visitRequestAddress = visitRequest.getAddress();
      AddressDTO userAddress = user.getAddress();

      if (!visitRequestAddress.equals(userAddress)) {
        int addressId = this.daoAddress.addAddress(visitRequestAddress);
        if (addressId == -1) {
          this.dalServices.rollbackTransaction();
          return null;
        } else {
          visitRequestAddress.setId(addressId);
          visitRequest.setAddress(visitRequestAddress);
        }
      } else {
        visitRequest.setAddress(null);
      }
      visitRequest.setStatus(VisitRequestStatus.en_attente);
      visitRequest.setTimeSlot(StringEscapeUtils.escapeHtml4(visitRequest.getTimeSlot()));
      visitRequest.setRequestDate(visitRequest.getRequestDate());
      int visitRequestId = this.daoVisitRequest.addVisitRequest(newVisitRequest);
      if (visitRequestId == -1) {
        this.dalServices.rollbackTransaction();
        return null;
      }
      visitRequest.setId(visitRequestId);
      visitRequest.setFurnitureList(visitRequest.getFurnitureList());

      for (FurnitureDTO furniture : visitRequest.getFurnitureList()) {
        furniture.setVisitRequest(visitRequest);
        furniture.setCondition(FurnitureCondition.propose);
        if (this.daoFurniture.insertFurniture(furniture) == -1) {
          this.dalServices.rollbackTransaction();
          return null;
        }
      }

      this.dalServices.commitTransaction();
      return visitRequest;
    } finally {
      dalServices.closeConnection();
    }
  }
}
