package be.vinci.pae.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitRequestImpl implements VisitRequest {

  private int id;
  private LocalDateTime requestDate;
  private String timeSlot;
  private Address address;
  private String status;
  private LocalDateTime chosenDateTime;
  private String cancellationReason;
  private UserDTO customer;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public LocalDateTime getRequestDate() {
    return requestDate;
  }

  @Override
  public String getTimeSlot() {
    return timeSlot;
  }

  @Override
  public Address getAddress() {
    return address;
  }

  @Override
  public String getStatus() {
    return status;
  }

  @Override
  public LocalDateTime getChosenDateTime() {
    return chosenDateTime;
  }

  @Override
  public String getCancellationReason() {
    return cancellationReason;
  }

  @Override
  public UserDTO getCustomer() {
    return customer;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void setRequestDate(LocalDateTime requestDate) {
    this.requestDate = requestDate;
  }

  @Override
  public void setTimeSlot(String timeSlot) {
    this.timeSlot = timeSlot;
  }

  @Override
  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public void setChosenDateTime(LocalDateTime chosenDateTime) {
    this.chosenDateTime = chosenDateTime;
  }

  @Override
  public void setCancellationReason(String cancellationReason) {
    this.cancellationReason = cancellationReason;
  }

  @Override
  public void setCustomer(UserDTO customer) {
    this.customer = customer;
  }



}
