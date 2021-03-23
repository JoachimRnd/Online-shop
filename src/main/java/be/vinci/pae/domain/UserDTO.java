package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;

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

  void setUserType(String userType);

  Address getAddress();

  void setAddress(Address address);

  LocalDateTime getRegistrationDate();

  void setRegistrationDate(LocalDateTime registrationDate);

  boolean isValidRegistration();

  void setValidRegistration(boolean validRegistration);

  String getPassword();

  void setPassword(String password);

}
