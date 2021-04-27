package be.vinci.pae.services.visitrequest;

import be.vinci.pae.domain.visitrequest.VisitRequestDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestFactory;
import be.vinci.pae.services.address.DAOAddress;
import be.vinci.pae.services.user.DAOUser;
import be.vinci.pae.utils.ValueLink.VisitRequestStatus;
import jakarta.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DAOVisitRequestImpl implements DAOVisitRequest {
  
  @Inject
  private VisitRequestFactory visitRequestFactory;

  @Inject
  private DAOAddress daoAddress;

  @Inject
  private DAOUser daoUser;

  /*
   * querySelectVisitRequestById = "SELECT v.visit_request_id, v.request_date, v.time_slot, " +
   * "v.address, v.status, v.chosen_date_time, v.cancellation_reason, v.customer " + "FROM project.visit_requests v WHERE v.visit_request_id  = ?";
   */

  /**
   * constructor of DAOVisitRequestImpl. contains queries.
   */
  public DAOVisitRequestImpl() {

  }

  private VisitRequestDTO createVisitRequest(ResultSet rs) throws SQLException {
    VisitRequestDTO visitRequest = null;
    if (rs.next()) {
      visitRequest = this.visitRequestFactory.getVisitRequest();
      visitRequest.setId(rs.getInt("visit_request_id"));
      visitRequest.setRequestDate(rs.getDate("request_date"));
      visitRequest.setTimeSlot(rs.getString("time_slot"));
      visitRequest.setAddress(daoAddress.getAddressById(rs.getInt("address")));
      visitRequest.setStatus(VisitRequestStatus.values()[rs.getInt("status")]);
      visitRequest.setChosenDateTime(rs.getDate("chosen_date_time"));
      visitRequest.setCancellationReason(rs.getString("cancellation_reason"));
      visitRequest.setCustomer(this.daoUser.getUserById(rs.getInt("customer")));
    }
    return visitRequest;
  }

  @Override
  public VisitRequestDTO selectVisitRequestById(int id) {
    // TODO Auto-generated method stub
    return null;
    /*
     * VisitRequestDTO visitRequest = null; try { PreparedStatement selectVisitRequestById =
     * dalServices.getPreparedStatement(querySelectVisitRequestById); selectVisitRequestById.setInt(1, id); try (ResultSet rs =
     * selectVisitRequestById.executeQuery()) { visitRequest = this.visitRequestFactory.getVisitRequest(); while (rs.next()) {
     * visitRequest.setId(rs.getInt("visit_request_id")); visitRequest.setRequestDate(rs.getDate("request_date"));
     * visitRequest.setTimeSlot(rs.getString("time_slot")); visitRequest.setStatus(String.valueOf(rs.getInt("status")));
     * visitRequest.setChosenDateTime(rs.getDate("chosen_date_time")); visitRequest.setCancellationReason(rs.getString("cancellation_reason"));
     * visitRequest.setCustomer(this.daoUser.getUserById(rs.getInt("customer"))); } return visitRequest; } } catch (SQLException e) { e.printStackTrace();
     * throw new FatalException("Data error : selectTypeById"); }
     */
  }
}
