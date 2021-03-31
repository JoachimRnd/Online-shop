package be.vinci.pae.services;

import be.vinci.pae.domain.AddressDTO;
import be.vinci.pae.domain.FurnitureDTO;
import be.vinci.pae.domain.VisitRequestDTO;
import be.vinci.pae.domain.VisitRequestFactory;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOVisitRequestImpl implements DAOVisitRequest {

  // private String querySelectAdressUser;
  // private String querySelectTypesFurniture;
  private String querySelectVisitRequestById;

  @Inject
  private VisitRequestFactory visitRequestFactory;

  @Inject
  private DalBackendServices dalServices;

  @Inject
  private DAOUser daoUser;

  /**
   * constructor of DAOVisitRequestImpl. contains queries.
   */
  public DAOVisitRequestImpl() {
    querySelectVisitRequestById = "SELECT v.visit_request_id, v.request_date, v.time_slot, "
        + "v.address, v.status, v.chosen_date_time, v.cancellation_reason, v.customer "
        + "FROM project.visit_requests v WHERE v.visit_request_id  = ?";
  }

  @Override
  public AddressDTO getAddressByUserId(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String[] getTypesOfFurniture() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int addPicture(String path) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int addFurniture(FurnitureDTO furniture) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int addVisitRequest(VisitRequestDTO visitRequest) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public VisitRequestDTO selectVisitRequestById(int id) {
    VisitRequestDTO visitRequest = null;
    try {
      PreparedStatement selectVisitRequestById =
          dalServices.getPreparedStatement(querySelectVisitRequestById);
      selectVisitRequestById.setInt(1, id);
      try (ResultSet rs = selectVisitRequestById.executeQuery()) {
        visitRequest = this.visitRequestFactory.getVisitRequest();
        while (rs.next()) {
          visitRequest.setId(rs.getInt("visit_request_id"));
          visitRequest.setRequestDate(rs.getDate("request_date"));
          visitRequest.setTimeSlot(rs.getString("time_slot"));
          // Address TODO
          // TODO CHANGER Status int en string
          visitRequest.setStatus(String.valueOf(rs.getInt("status")));
          visitRequest.setChosenDateTime(rs.getDate("chosen_date_time"));
          visitRequest.setCancellationReason(rs.getString("cancellation_reason"));
          visitRequest.setCustomer(this.daoUser.getUserById(rs.getInt("customer")));

        }
        return visitRequest;
      }
    } catch (

        Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectTypeById");
    }
  }


}
