package be.vinci.pae.services.visitrequest;

import be.vinci.pae.domain.visitrequest.VisitRequestDTO;

public class DAOVisitRequestImpl implements DAOVisitRequest {

  /*
  querySelectVisitRequestById = "SELECT v.visit_request_id, v.request_date, v.time_slot, "
        + "v.address, v.status, v.chosen_date_time, v.cancellation_reason, v.customer "
        + "FROM project.visit_requests v WHERE v.visit_request_id  = ?";
   */

  /**
   * constructor of DAOVisitRequestImpl. contains queries.
   */
  public DAOVisitRequestImpl() {

  }

  @Override
  public int addVisitRequest(VisitRequestDTO visitRequest) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public VisitRequestDTO selectVisitRequestById(int id) {
    //TODO Auto-generated method stub
    return null;
    /*VisitRequestDTO visitRequest = null;
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
          visitRequest.setStatus(String.valueOf(rs.getInt("status")));
          visitRequest.setChosenDateTime(rs.getDate("chosen_date_time"));
          visitRequest.setCancellationReason(rs.getString("cancellation_reason"));
          visitRequest.setCustomer(this.daoUser.getUserById(rs.getInt("customer")));

        }
        return visitRequest;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectTypeById");
    }*/
  }


}
