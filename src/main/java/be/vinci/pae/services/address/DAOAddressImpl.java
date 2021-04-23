package be.vinci.pae.services.address;

import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOAddressImpl implements DAOAddress {

  @Inject
  DalBackendServices dalBackendServices;
  private String querySelectAllCommunes;

  public DAOAddressImpl() {
    querySelectAllCommunes = "SELECT DISTINCT a.commune FROM project.addresses a";
  }

  @Override
  public List<String> getAllCommunes() {
    try {
      PreparedStatement selectAllCommunes = this.dalBackendServices
          .getPreparedStatement(querySelectAllCommunes);
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
}
