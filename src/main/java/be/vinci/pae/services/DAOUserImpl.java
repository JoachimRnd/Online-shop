package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.vinci.pae.domain.Address;
import be.vinci.pae.domain.AddressFactory;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserFactory;
import jakarta.inject.Inject;

public class DAOUserImpl implements DAOUser {

  private PreparedStatement selectUserByUsername;
  private PreparedStatement selectUserById;
  private String querySelectUserByUsername;
  private String querySelectUserById;

  @Inject
  private UserFactory userFactory;

  @Inject
  private AddressFactory addressFactory;

  @Inject
  private DalServices dalServices;

  /**
   * Implementation of User selected by Id and User selected by username.
   *
   */
  public DAOUserImpl() {
    querySelectUserByUsername =
        "SELECT u.user_id, u.username, u.password, u.last_name, u.first_name, "
            + "a.street, a.building_number, a.unit_number, a.postcode, a.commune, a.country, u.email, u.registration_date,"
            + " u.valid_registration, u.suer_type FROM project.addresses a, project.users u "
            + "WHERE u.username = ? AND u.address = a.address_id";
    querySelectUserById = "SELECT u.user_id, u.username, u.password, u.last_name, u.first_name, "
        + "a.street, a.building_number, a.unit_number, a.postcode, a.commune, a.country, u.email, u.registration_date,"
        + " u.valid_registration, u.user_type FROM project.addresses a, project.users u "
        + "WHERE u.user_id = ? AND u.address = a.address_id";
  }

  @Override
  public UserDTO getUser(String login) {
    try {
      if (selectUserByUsername == null) {
        selectUserByUsername = this.dalServices.getPreparedStatement(querySelectUserByUsername);
      }
      selectUserByUsername.setString(1, login);
      try (ResultSet rs = selectUserByUsername.executeQuery()) {
        UserDTO user = createUser(rs);
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public UserDTO getUser(int id) {
    try {
      if (selectUserById == null) {
        selectUserById = this.dalServices.getPreparedStatement(querySelectUserById);
      }
      selectUserById.setInt(1, id);
      try (ResultSet rs = selectUserById.executeQuery()) {
        UserDTO user = createUser(rs);
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private UserDTO createUser(ResultSet rs) throws SQLException {
    UserDTO user = null;
    while (rs.next()) {
      user = this.userFactory.getUser();
      user.setId(rs.getInt("user_id"));
      user.setUsername(rs.getString("username"));
      user.setPassword(rs.getString("password"));
      user.setLastName(rs.getString("last_name"));
      user.setFirstName(rs.getString("first_name"));
      Address address = this.addressFactory.getAddress();
      address.setStreet(rs.getString("street"));
      address.setBuildingNumber(rs.getString("building_number"));
      address.setUnitNumber(rs.getString("unit_number"));
      address.setPostcode(rs.getString("postcode"));
      address.setCommune(rs.getString("commune"));
      address.setCountry(rs.getString("country"));
      user.setAddress(address);
      user.setEmail(rs.getString("email"));
      user.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
      user.setValidRegistration(rs.getBoolean("valid_registration"));
      user.setUserType(rs.getInt("user_type"));
    }
    return user;
  }

  // @TODO Implémenter l'ajout d'un User
  @Override
  public void addUser(UserDTO user) {

  }
}
