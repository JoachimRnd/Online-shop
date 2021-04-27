package be.vinci.pae.services.visitrequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import be.vinci.pae.domain.visitrequest.VisitRequestDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestFactory;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.services.user.DAOUser;
import be.vinci.pae.utils.FatalException;
import be.vinci.pae.utils.ValueLink.VisitRequestStatus;
import jakarta.inject.Inject;


public class DAOVisitRequestImpl implements DAOVisitRequest {

  private String queryAddVisitRequest;
  private String querySelectVisitRequestByUserAndFurniture;

  @Inject
  private DalBackendServices dalServices;

  @Inject
  private VisitRequestFactory visitRequestFactory;

  @Inject
  private DAOUser daoUser;

  /*
   * querySelectVisitRequestById = "SELECT v.visit_request_id, v.request_date, v.time_slot, " + <<<<<<< HEAD
   * "v.address, v.status, v.chosen_date_time, v.cancellation_reason, v.customer " + "FROM project.visit_requests v WHERE v.visit_request_id  = ?";
   * ======= "v.address, v.status, v.chosen_date_time, v.cancellation_reason, v.customer " +
   * "FROM project.visit_requests v WHERE v.visit_request_id  = ?"; >>>>>>> refs/remotes/origin/master
   */

  /**
   * constructor of DAOVisitRequestImpl. contains queries.
   */
  public DAOVisitRequestImpl() {
    queryAddVisitRequest =
        "INSERT INTO project.visit_requests (visit_request_id, request_date, time_slot, address,"
            + " status, chosen_date_time, cancellation_reason, customer) VALUES"
            + " (DEFAULT,?,?,?,?,NULL,NULL,?)";
    querySelectVisitRequestByUserAndFurniture = "SELECT v.visit_request_id, v.request_date, "
        + "v.time_slot, v.address, v.status, v.chosen_date_time, v.cancellation_reason, v.customer "
        + "FROM project.visit_requests v, project.furniture f WHERE v.customer = ? "
        + "AND f.furniture_id = ? AND v.visit_request_id = f.visit_request;";
  }

  @Override
  public int addVisitRequest(VisitRequestDTO visitRequest) {
    int visitRequestId = -1;
    try {
      PreparedStatement insertVisitRequest =
          this.dalServices.getPreparedStatementAdd(queryAddVisitRequest);
      insertVisitRequest.setTimestamp(1, Timestamp.from(visitRequest.getRequestDate().toInstant()));
      insertVisitRequest.setString(2, visitRequest.getTimeSlot());
      if (visitRequest.getAddress() == null) {
        insertVisitRequest.setNull(3, java.sql.Types.INTEGER);
      } else {
        insertVisitRequest.setInt(3, visitRequest.getAddress().getId());
      }
      insertVisitRequest.setInt(4, visitRequest.getStatus().ordinal());
      insertVisitRequest.setInt(5, visitRequest.getCustomer().getId());
      insertVisitRequest.execute();
      try (ResultSet rs = insertVisitRequest.getGeneratedKeys()) {
        if (rs.next()) {
          visitRequestId = rs.getInt(1);
        }
        return visitRequestId;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : insertVisitRequest");
    }
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

  @Override
  public VisitRequestDTO selectVisitRequestByUserAndFurniture(int furnitureId, int userId) {
    try {
      PreparedStatement selectVisitRequestByUserAndFurniture =
          dalServices.getPreparedStatement(querySelectVisitRequestByUserAndFurniture);
      selectVisitRequestByUserAndFurniture.setInt(1, userId);
      selectVisitRequestByUserAndFurniture.setInt(2, furnitureId);
      try (ResultSet rs = selectVisitRequestByUserAndFurniture.executeQuery()) {
        VisitRequestDTO vs = createVisitRequest(rs);
        return vs;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectVisitRequestByUserAndFurniture");
    }

  }

  private VisitRequestDTO createVisitRequest(ResultSet rs) throws SQLException {
    VisitRequestDTO visitRequest = null;
    if (rs.next()) {
      visitRequest = this.visitRequestFactory.getVisitRequest();
      visitRequest.setId(rs.getInt("visit_request_id"));
      visitRequest.setRequestDate(rs.getDate("request_date"));
      visitRequest.setTimeSlot(rs.getString("time_slot"));
      visitRequest.setAddress(null); // TODO
      visitRequest.setStatus(VisitRequestStatus.values()[rs.getInt("status")]);
      visitRequest.setChosenDateTime(rs.getDate("chosen_date_time"));
      visitRequest.setCancellationReason(rs.getString("cancellation_reason"));
      visitRequest.setCustomer(this.daoUser.getUserById(rs.getInt("customer")));

    }
    return visitRequest;
  }


}
