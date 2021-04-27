package be.vinci.pae.services.address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.domain.address.AddressDTO;
import be.vinci.pae.domain.address.AddressFactory;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;

public class DAOAddressImpl implements DAOAddress {

  @Inject
  DalBackendServices dalBackendServices;

  @Inject
  AddressFactory addressFactory;

  private String querySelectAllCommunes;
  private String queryAddAddress;
  private String querySelectAddressByUserId;

  /**
   * constructor of DAOAddressImpl. contains queries.
   */
  public DAOAddressImpl() {
    querySelectAllCommunes = "SELECT DISTINCT a.commune FROM project.addresses a";
    queryAddAddress = "INSERT INTO project.addresses (address_id, street, "
        + "building_number, unit_number, postcode, commune, country) "
        + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    querySelectAddressByUserId =
        "SELECT a.address_id, a.street, a.building_number, a.unit_number, a.postcode,"
            + " a.commune, a.country FROM project.addresses a, project.users u"
            + " WHERE a.address_id = u.address AND u.user_id = ?";
  }

  @Override
  public List<String> getAllCommunes() {
    try {
      PreparedStatement selectAllCommunes =
          this.dalBackendServices.getPreparedStatement(querySelectAllCommunes);
      List<String> allCommunes = new ArrayList<>();
      try (ResultSet rs = selectAllCommunes.executeQuery()) {
        while (rs.next()) {
          allCommunes.add(rs.getString(1));
        }
      }
      return allCommunes;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : getAllCommunes");
    }
  }

  @Override
  public int addAddress(AddressDTO address) {
    int addressId = -1;
    try {
      PreparedStatement insertAddress =
          this.dalBackendServices.getPreparedStatementAdd(queryAddAddress);
      insertAddress.setString(1, address.getStreet());
      insertAddress.setString(2, address.getBuildingNumber());
      insertAddress.setString(3, address.getUnitNumber());
      insertAddress.setString(4, address.getPostcode());
      insertAddress.setString(5, address.getCommune());
      insertAddress.setString(6, address.getCountry());
      insertAddress.execute();
      try (ResultSet rs = insertAddress.getGeneratedKeys()) {
        if (rs.next()) {
          addressId = rs.getInt(1);
        }
        return addressId;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : insertAddress");
    }
  }

  @Override
  public AddressDTO getAddressByUserId(int userId) {
    try {
      PreparedStatement selectAddressByUserId =
          this.dalBackendServices.getPreparedStatement(querySelectAddressByUserId);
      selectAddressByUserId.setInt(1, userId);
      try (ResultSet rs = selectAddressByUserId.executeQuery()) {
        return createAddress(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : getAddressByUserId");
    }
  }

  private AddressDTO createAddress(ResultSet rs) throws SQLException {
    AddressDTO address = null;
    if (rs.next()) {
      address = this.addressFactory.getAddress();
      address.setId(rs.getInt("address_id"));
      address.setStreet(rs.getString("street"));
      address.setBuildingNumber(rs.getString("building_number"));
      address.setUnitNumber(rs.getString("unit_number"));
      address.setPostcode(rs.getString("postcode"));
      address.setCommune(rs.getString("commune"));
      address.setCountry(rs.getString("country"));
    }
    return address;
  }
}
