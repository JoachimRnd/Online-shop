package be.vinci.pae.domain;

import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import views.Views;

// ignore all null fields in order to avoid sending props not linked to a JSON view
@JsonInclude(JsonInclude.Include.NON_NULL)
class UserImpl implements User {

  @JsonView(Views.Public.class)
  private int id;
  @JsonView(Views.Public.class)
  private String username;
  @JsonView(Views.Public.class)
  private String lastName;
  @JsonView(Views.Public.class)
  private String firstName;
  @JsonView(Views.Public.class)
  private String email;
  @JsonView(Views.Public.class)
  private int userType;
  @JsonView(Views.Public.class)
  private Address address;

  @JsonView(Views.Internal.class)
  private LocalDateTime registrationDate;
  @JsonView(Views.Internal.class)
  private boolean validRegistration;
  @JsonView(Views.Internal.class)
  private String password;

  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public int getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getEmail() {
    return email;
  }

  public int getUserType() {
    return userType;
  }

  public Address getAddress() {
    return address;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public boolean isValidRegistration() {
    return validRegistration;
  }

  public String getPassword() {
    return password;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void setUsername(String username) {
    this.username = username;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setUserType(int userType) {
    this.userType = userType;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }

  public void setValidRegistration(boolean validRegistration) {
    this.validRegistration = validRegistration;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
