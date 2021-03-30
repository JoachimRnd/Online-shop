package be.vinci.pae.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitRequestImpl implements VisitRequest {

  private int id;
  private Date requestDate;
  private String timeSlot;
  private AddressDTO address;
  private String status;
  private Date chosenDateTime;
  private String cancellationReason;
  private UserDTO customer;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public Date getRequestDate() {
    return requestDate;
  }

  @Override
  public void setRequestDate(Date requestDate) {
    this.requestDate = requestDate;
  }

  @Override
  public String getTimeSlot() {
    return timeSlot;
  }

  @Override
  public void setTimeSlot(String timeSlot) {
    this.timeSlot = timeSlot;
  }

  @Override
  public AddressDTO getAddress() {
    return address;
  }

  @Override
  public void setAddress(AddressDTO address) {
    this.address = address;
  }

  @Override
  public String getStatus() {
    return status;
  }

  @Override
  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public Date getChosenDateTime() {
    return chosenDateTime;
  }

  @Override
  public void setChosenDateTime(Date chosenDateTime) {
    this.chosenDateTime = chosenDateTime;
  }

  @Override
  public String getCancellationReason() {
    return cancellationReason;
  }

  @Override
  public void setCancellationReason(String cancellationReason) {
    this.cancellationReason = cancellationReason;
  }

  @Override
  public UserDTO getCustomer() {
    return customer;
  }

  @Override
  public void setCustomer(UserDTO customer) {
    this.customer = customer;
  }


}
