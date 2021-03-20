package be.vinci.pae.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = VisitRequestImpl.class)
public interface VisitRequestDTO {
  public int getId();

  public LocalDateTime getRequestDate();

  public String getTimeSlot();

  public Address getAddress();

  public String getStatus();

  public LocalDateTime getChosenDateTime();

  public String getCancellationReason();

  public UserDTO getCustomer();

  public void setId(int id);

  public void setRequestDate(LocalDateTime requestDate);

  public void setTimeSlot(String timeSlot);

  public void setAddress(Address address);

  public void setStatus(String status);

  public void setChosenDateTime(LocalDateTime chosenDateTime);

  public void setCancellationReason(String cancellationReason);

  public void setCustomer(UserDTO customer);
}
