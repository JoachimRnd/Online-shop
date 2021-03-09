package be.vinci.pae.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {

  int getId();

  String getUsername();

  String getLastName();

  String getFirstName();

  String getEmail();

  int getUserType();

  Address getAddress();

  LocalDateTime getRegistrationDate();

  boolean isValidRegistration();

  void setId(int id);

  void setUsername(String username);

  void setLastName(String lastName);

  void setFirstName(String firstName);

  void setEmail(String email);

  void setUserType(int userType);

  void setAddress(Address address);

  void setRegistrationDate(LocalDateTime registrationDate);

  void setValidRegistration(boolean validRegistration);

  String getPassword();

  void setPassword(String password);

  @Override
  String toString();

}
