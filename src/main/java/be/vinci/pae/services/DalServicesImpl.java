package be.vinci.pae.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbcp2.BasicDataSource;
import be.vinci.pae.utils.Config;

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
    try {
      return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }


  @Override
  public void startTransaction() {
    Connection conn = null;
    try {
      conn = bds.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    tl.set(conn);
  }

  // TODO pour les query get, pas utile d'auto commit -> faire une methode diff?
  @Override
  public void startTransactionSetAutoCommitToFalse() {
    startTransaction();
    try {
      tl.get().setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }



  @Override
  public void commitTransaction() {
    Connection conn = tl.get();
    tl.remove();
    try {
      conn.commit();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void rollbackTransaction() {
    Connection conn = tl.get();
    tl.remove();
    try {
      conn.rollback();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }



}
