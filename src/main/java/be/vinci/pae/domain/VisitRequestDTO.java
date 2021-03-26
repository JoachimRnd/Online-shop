package be.vinci.pae.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = VisitRequestImpl.class)
public interface VisitRequestDTO {
  int getId();

  LocalDateTime getRequestDate();

  String getTimeSlot();

  Address getAddress();

  String getStatus();

  LocalDateTime getChosenDateTime();

  String getCancellationReason();

  UserDTO getCustomer();

  void setId(int id);

  void setRequestDate(LocalDateTime requestDate);

  void setTimeSlot(String timeSlot);

  void setAddress(Address address);

  void setStatus(String status);

  void setChosenDateTime(LocalDateTime chosenDateTime);

  void setCancellationReason(String cancellationReason);

  void setCustomer(UserDTO customer);
}
