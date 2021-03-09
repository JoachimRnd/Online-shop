package be.vinci.pae.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import be.vinci.pae.utils.Config;

public class DalServicesImpl implements DalServices {

  private static final String DB_STRING_CONNECTION = Config.getProperty("DatabaseStringConnection");
  private Connection conn;

  /**
   * Connection to Database.
   *
   * @TODO Javadoc
   */
  public DalServicesImpl() {
    try {
      conn = DriverManager.getConnection(DB_STRING_CONNECTION);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @Override
  public PreparedStatement getPreparedStatement(String query) {
    try {
      return conn.prepareStatement(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

}
