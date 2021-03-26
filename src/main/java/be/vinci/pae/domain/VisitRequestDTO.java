package be.vinci.pae.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = VisitRequestImpl.class)
public interface VisitRequestDTO {
  public int getId();

  LocalDateTime getRequestDate();

  String getTimeSlot();

  Address getAddress();

  String getStatus();

  LocalDateTime getChosenDateTime();

  String getCancellationReason();

  UserDTO getCustomer();

  void setId(int id);

  void setRequestDate(LocalDateTime requestDate);

  public void setTimeSlot(String timeSlot);

  public void setAddress(Address address);

  public void setStatus(String status);

  public void setChosenDateTime(LocalDateTime chosenDateTime);

  public void setCancellationReason(String cancellationReason);

  public void setCustomer(UserDTO customer);
}
