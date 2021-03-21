package be.vinci.pae.services;

import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbcp2.BasicDataSource;

public class DalServicesImpl implements DalServices, DalBackendServices {

  private static final String DB_STRING_CONNECTION = Config.getProperty("DatabaseStringConnection");
  private static final String DB_USERNAME = Config.getProperty("DatabaseUsername");
  private static final String DB_PASSWORD = Config.getProperty("DatabasePassword");
  private static BasicDataSource bds;
  private static final ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

  /**
   * Create pool of Connection.
   */
  public DalServicesImpl() {
    bds = new BasicDataSource();
    bds.setUrl(DB_STRING_CONNECTION);
    bds.setUsername(DB_USERNAME);
    bds.setPassword(DB_PASSWORD);
    bds.setMinIdle(5);
    bds.setMaxIdle(10);
    bds.setMaxOpenPreparedStatements(10);
  }

  @Override
  public PreparedStatement getPreparedStatement(String query) {
    Connection conn = tl.get();
    if (conn == null) {
      conn = getConnection();
    }
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = conn.prepareStatement(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return preparedStatement;
  }

  @Override
  public PreparedStatement getPreparedStatementAdd(String query) {
    Connection conn = tl.get();
    if (conn == null) {
      conn = getConnection();
    }
    try {
      return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  private Connection getConnection() {
    Connection conn = null;
    try {
      conn = bds.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    tl.set(conn);
    return conn;
  }

  @Override
  public void startTransaction() {
    Connection conn = tl.get();
    if (conn == null) {
      conn = getConnection();
    }
    try {
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void commitTransaction() {
    Connection conn = tl.get();
    try {
      conn.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void rollbackTransaction() {
    Connection conn = tl.get();
    try {
      conn.rollback();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void closeConnection() {
    Connection conn = tl.get();
    tl.remove();
    if (conn == null) {
      return;
    }
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


}
