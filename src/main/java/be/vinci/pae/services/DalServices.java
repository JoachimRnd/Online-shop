package be.vinci.pae.services;

import java.sql.PreparedStatement;

public interface DalServices {

  PreparedStatement getPreparedStatement(String query);

  PreparedStatement getPreparedStatementAdd(String query);

}
