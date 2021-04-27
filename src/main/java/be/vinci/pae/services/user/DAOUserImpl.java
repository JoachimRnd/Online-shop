package be.vinci.pae.services.user;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import be.vinci.pae.domain.address.AddressDTO;
import be.vinci.pae.domain.address.AddressFactory;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.user.UserFactory;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.utils.FatalException;
import be.vinci.pae.utils.ValueLink.UserType;
import jakarta.inject.Inject;

public class DAOUserImpl implements DAOUser {

  private String querySelectUserByUsername;
  private String querySelectUserById;
  private String querySelectUserByEmail;
  private String querySelectAddressIdWithUnitNumber;
  private String querySelectAddressIdWithoutUnitNumber;
  private String queryAddAddressWithUnitNumber;
  private String queryAddAddressWithoutUnitNumber;
  private String queryAddUser;
  private String queryValidateUser;
  private String querySelectUnvalidatedUsers;
  private String querySelectAllUsername;
  private String querySelectUsersFiltered;

  @Inject
  private UserFactory userFactory;

  @Inject
  private AddressFactory addressFactory;

  @Inject
  private DalBackendServices dalBackendServices;

  /**
   * Implementation of User selected by Id and User selected by username.
   */
  public DAOUserImpl() {
    querySelectUserByUsername =
        "SELECT u.user_id, u.username, u.password, u.last_name, u.first_name, a.address_id,"
            + " a.street,"
            + " a.building_number, a.unit_number, a.postcode, a.commune, a.country, u.email,"
            + " u.registration_date, u.valid_registration, u.user_type FROM project.addresses a,"
            + "project.users u WHERE u.username = ? AND u.address = a.address_id";
    querySelectUserById = "SELECT u.user_id, u.username, u.password, u.last_name, u.first_name, "
        + "a.address_id,"
        + "a.street, a.building_number, a.unit_number, a.postcode, a.commune, a.country, u.email,"
        + "u.registration_date, u.valid_registration, u.user_type FROM project.addresses a,"
        + " project.users u WHERE u.user_id = ? AND u.address = a.address_id";
    querySelectUserByEmail = "SELECT u.user_id, u.username, u.password, u.last_name, u.first_name,"
        + "a.address_id, "
        + "a.street, a.building_number, a.unit_number, a.postcode, a.commune, a.country, u.email,"
        + "u.registration_date, u.valid_registration, u.user_type FROM project.addresses a,"
        + " project.users u WHERE u.email = ? AND u.address = a.address_id";
    querySelectAddressIdWithUnitNumber =
        "SELECT a.address_id FROM project.addresses a WHERE a.street = ? AND "
            + "a.building_number = ? AND a.postcode = ? AND "
            + "a.commune = ? AND a.country = ? AND a.unit_number = ?";
    querySelectAddressIdWithoutUnitNumber =
        "SELECT a.address_id FROM project.addresses a WHERE a.street = ? AND "
            + "a.building_number = ? AND a.postcode = ? AND a.commune = ? AND a.country = ?";
    queryAddAddressWithUnitNumber = "INSERT INTO project.addresses (address_id, street, "
        + "building_number, postcode, commune, country, unit_number) "
        + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    queryAddAddressWithoutUnitNumber = "INSERT INTO project.addresses (address_id, street, "
        + "building_number, postcode, commune, country) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    queryAddUser = "INSERT INTO project.users (user_id, username, last_name, first_name, "
        + "email, password, address, registration_date, valid_registration) "
        + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
    queryValidateUser = "UPDATE project.users " + "SET user_type = ?, valid_registration = ? "
        + "WHERE user_id = ?";
    querySelectUnvalidatedUsers =
        "SELECT u.user_id, u.username, u.password, u.last_name, " + "u.first_name, a.address_id,"
            + "a.street, a.building_number, a.unit_number, a.postcode, a.commune, "
            + "a.country, u.email, u.registration_date, u.valid_registration, u.user_type "
            + "FROM project.addresses a, project.users u "
            + "WHERE u.valid_registration = false AND u.address = a.address_id";
    querySelectAllUsername = "SELECT DISTINCT u.username FROM project.users u";
    querySelectUsersFiltered =
        "SELECT u.user_id, u.username, u.password, u.last_name, " + "u.first_name,a.address_id,"
            + "a.street, a.building_number, a.unit_number, a.postcode, a.commune, "
            + "a.country, u.email, u.registration_date, u.valid_registration, u.user_type "
            + "FROM project.addresses a, project.users u "
            + "WHERE u.address = a.address_id AND lower(u.username) LIKE lower(?) AND "
            + "lower(a.postcode) LIKE lower(?) AND lower(a.commune) LIKE lower(?)";
  }

  @Override
  public UserDTO getUserByUsername(String username) {
    try {

      PreparedStatement selectUserByUsername =
          this.dalBackendServices.getPreparedStatement(querySelectUserByUsername);

      selectUserByUsername.setString(1, username);
      try (ResultSet rs = selectUserByUsername.executeQuery()) {
        UserDTO user = createUser(rs);
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : getUserByUsername");
    }
  }

  @Override
  public UserDTO getUserByEmail(String email) {
    try {

      PreparedStatement selectUserByEmail =
          this.dalBackendServices.getPreparedStatement(querySelectUserByEmail);
      selectUserByEmail.setString(1, email);
      try (ResultSet rs = selectUserByEmail.executeQuery()) {
        UserDTO user = createUser(rs);
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : getUserByEmail");
    }
  }

  @Override
  public UserDTO getUserById(int id) {
    try {
      PreparedStatement selectUserById =
          this.dalBackendServices.getPreparedStatement(querySelectUserById);
      selectUserById.setInt(1, id);
      try (ResultSet rs = selectUserById.executeQuery()) {
        UserDTO user = createUser(rs);
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : getUserById");
    }
  }

  private UserDTO createUser(ResultSet rs) throws SQLException {
    UserDTO user = null;
    if (rs.next()) {
      user = this.userFactory.getUser();
      user.setId(rs.getInt("user_id"));
      user.setUsername(rs.getString("username"));
      user.setPassword(rs.getString("password"));
      user.setLastName(rs.getString("last_name"));
      user.setFirstName(rs.getString("first_name"));
      AddressDTO address = this.addressFactory.getAddress();
      address.setId(rs.getInt("address_id"));
      address.setStreet(rs.getString("street"));
      address.setBuildingNumber(rs.getString("building_number"));
      address.setUnitNumber(rs.getString("unit_number"));
      address.setPostcode(rs.getString("postcode"));
      address.setCommune(rs.getString("commune"));
      address.setCountry(rs.getString("country"));
      user.setAddress(address);
      user.setEmail(rs.getString("email"));
      user.setRegistrationDate(Date.from(rs.getTimestamp("registration_date").toInstant()));
      user.setValidRegistration(rs.getBoolean("valid_registration"));
      user.setUserType(UserType.values()[rs.getInt("user_type")]);
    }
    return user;
  }

  @Override
  public int addUser(UserDTO user) {
    // get address if exist (récup id et mettre dans user sinon add address)
    long addressId = -1;
    try {
      PreparedStatement selectAddressId;
      if (user.getAddress().getUnitNumber() != null) {
        PreparedStatement selectAddressIdWithUnitNumber =
            this.dalBackendServices.getPreparedStatement(querySelectAddressIdWithUnitNumber);
        selectAddressId = selectAddressIdWithUnitNumber;
        selectAddressId.setString(6, user.getAddress().getUnitNumber());
      } else {
        PreparedStatement selectAddressIdWithoutUnitNumber =
            this.dalBackendServices.getPreparedStatement(querySelectAddressIdWithoutUnitNumber);
        selectAddressId = selectAddressIdWithoutUnitNumber;
      }
      selectAddressId.setString(1, user.getAddress().getStreet());
      selectAddressId.setString(2, user.getAddress().getBuildingNumber());
      selectAddressId.setString(3, user.getAddress().getPostcode());
      selectAddressId.setString(4, user.getAddress().getCommune());
      selectAddressId.setString(5, user.getAddress().getCountry());
      try (ResultSet rs = selectAddressId.executeQuery()) {
        if (rs.next()) {
          addressId = rs.getLong("address_id");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : addUser get Address");
    }

    boolean noErrors = true;

    if (addressId == -1) {
      try {
        PreparedStatement addAddress;
        if (user.getAddress().getUnitNumber() != null) {
          PreparedStatement addAddressWithUnitNumber =
              this.dalBackendServices.getPreparedStatementAdd(queryAddAddressWithUnitNumber);
          addAddress = addAddressWithUnitNumber;
          addAddress.setString(6, user.getAddress().getUnitNumber());
        } else {
          PreparedStatement addAddressWithoutUnitNumber =
              this.dalBackendServices.getPreparedStatementAdd(queryAddAddressWithoutUnitNumber);
          addAddress = addAddressWithoutUnitNumber;
        }
        addAddress.setString(1, user.getAddress().getStreet());
        addAddress.setString(2, user.getAddress().getBuildingNumber());
        addAddress.setString(3, user.getAddress().getPostcode());
        addAddress.setString(4, user.getAddress().getCommune());
        addAddress.setString(5, user.getAddress().getCountry());

        addAddress.executeUpdate();

        try (ResultSet rs = addAddress.getGeneratedKeys()) {
          if (rs.next()) {
            addressId = rs.getLong(1);
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
        noErrors = false;
        throw new FatalException("Database error : addUser Add Address");
      }
    }

    // add user et recup id
    int userId = -1;
    if (noErrors) {
      try {
        PreparedStatement addUser = this.dalBackendServices.getPreparedStatementAdd(queryAddUser);
        addUser.setString(1, user.getUsername());
        addUser.setString(2, user.getLastName());
        addUser.setString(3, user.getFirstName());
        addUser.setString(4, user.getEmail());
        addUser.setString(5, user.getPassword());
        addUser.setLong(6, addressId);
        addUser.setTimestamp(7, Timestamp.from(user.getRegistrationDate().toInstant()));
        addUser.setBoolean(8, user.isValidRegistration());

        addUser.execute();

        try (ResultSet rs = addUser.getGeneratedKeys()) {
          if (rs.next()) {
            userId = rs.getInt(1);
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new FatalException("Database error : addUser");
      }
    }
    // return id
    return userId;
  }

  @Override
  public boolean validateUser(int id, int type) {
    try {
      PreparedStatement validateUser =
          this.dalBackendServices.getPreparedStatement(queryValidateUser);
      validateUser.setInt(1, type);
      validateUser.setBoolean(2, true);
      validateUser.setInt(3, id);
      return validateUser.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : validateUser");
    }
  }

  @Override
  public List<UserDTO> getUnvalidatedUsers() {
    try {
      PreparedStatement selectUnvalidatedUsers =
          this.dalBackendServices.getPreparedStatement(querySelectUnvalidatedUsers);
      List<UserDTO> unvalidatedUsers = new ArrayList<>();
      try (ResultSet rs = selectUnvalidatedUsers.executeQuery()) {
        UserDTO user;
        do {
          user = createUser(rs);
          unvalidatedUsers.add(user);
        } while (user != null);
        unvalidatedUsers.remove(unvalidatedUsers.size() - 1);
      }
      return unvalidatedUsers;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : getUnvalidatedUsers");
    }
  }

  @Override
  public List<String> getAllLastnames() {
    try {
      PreparedStatement selectAllUsers =
          this.dalBackendServices.getPreparedStatement(querySelectAllUsername);
      List<String> allUsers = new ArrayList<>();
      try (ResultSet rs = selectAllUsers.executeQuery()) {
        while (rs.next()) {
          allUsers.add(rs.getString(1));
        }
      }
      return allUsers;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : getAllUsers");
    }
  }

  @Override
  public List<UserDTO> getUsersFiltered(String username, String postcode, String commune) {
    try {
      PreparedStatement selectUsersFiltered =
          this.dalBackendServices.getPreparedStatement(querySelectUsersFiltered);
      selectUsersFiltered.setString(1, username + "%");
      selectUsersFiltered.setString(2, postcode + "%");
      selectUsersFiltered.setString(3, commune + "%");
      List<UserDTO> allUsers = new ArrayList<>();
      try (ResultSet rs = selectUsersFiltered.executeQuery()) {
        UserDTO user;
        do {
          user = createUser(rs);
          allUsers.add(user);
        } while (user != null);
        allUsers.remove(allUsers.size() - 1);
      }
      return allUsers;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : getUsersFiltered");
    }
  }
}
