package be.vinci.pae.domain.user;

import be.vinci.pae.domain.address.AddressDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {

  int getId();

  void setId(int id);

  String getUsername();

  void setUsername(String username);

  String getLastName();

  void setLastName(String lastName);

  String getFirstName();

  void setFirstName(String firstName);

  String getEmail();

  void setEmail(String email);

  String getUserType();

  void setUserTypeFromInt(int userType);

  AddressDTO getAddress();

  void setAddress(AddressDTO address);

  Date getRegistrationDate();

  void setRegistrationDate(Date registrationDate);

  boolean isValidRegistration();

  void setValidRegistration(boolean validRegistration);

  String getPassword();

  void setPassword(String password);

}
