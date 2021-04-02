package be.vinci.pae.domain.user;

import be.vinci.pae.domain.address.AddressDTO;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.ValueLiaison;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;
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
  private String userType;
  @JsonView(Views.Public.class)
  private AddressDTO address;
  @JsonView(Views.Public.class)
  private boolean validRegistration;

  @JsonView(Views.Internal.class)
  private Date registrationDate;

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

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public void setUsername(String username) {
    this.username = username;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    if (ValueLiaison.isValidUserType(userType)) {
      this.userType = userType;
    } else {
      throw new BusinessException("Wrong user type");
    }
  }

  public void setUserTypeFromInt(int userType) {
    this.userType = ValueLiaison.intToStringUserType(userType);
  }

  public AddressDTO getAddress() {
    return address;
  }

  public void setAddress(AddressDTO address) {
    this.address = address;
  }

  public Date getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  public boolean isValidRegistration() {
    return validRegistration;
  }

  public void setValidRegistration(boolean validRegistration) {
    this.validRegistration = validRegistration;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
