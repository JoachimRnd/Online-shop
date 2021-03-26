package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(as = VisitRequestImpl.class)
public interface VisitRequestDTO {

  int getId();

  void setId(int id);

  Date getRequestDate();

  void setRequestDate(Date requestDate);

  String getTimeSlot();

  void setTimeSlot(String timeSlot);

  AddressDTO getAddress();

  void setAddress(AddressDTO address);

  String getStatus();

  void setStatus(String status);

  Date getChosenDateTime();

  void setChosenDateTime(Date chosenDateTime);

  String getCancellationReason();

  void setCancellationReason(String cancellationReason);

  UserDTO getCustomer();

  void setCustomer(UserDTO customer);
}
