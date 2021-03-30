package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import be.vinci.pae.domain.TypeDTO;
import be.vinci.pae.domain.TypeFactory;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;

public class DAOTypeImpl implements DAOType {

  private String querySelectTypeId;

  @Inject
  private TypeFactory typeFactory;

  @Inject
  private DalBackendServices dalServices;

  public DAOTypeImpl() {
    super();
    querySelectTypeId =
        "SELECT t.type_id, t.name FROM project.furniture_types t WHERE t.type_id = ?";
  }

  @Override
  public TypeDTO selectTypeByName(String typeName) {
    // TODO Auto-generated method stub
    return null;
  }
  // TODO for later

  @Override
  public TypeDTO selectTypeById(int id) {
    TypeDTO type = null;
    try {
      PreparedStatement selectTypeById = dalServices.getPreparedStatement(querySelectTypeId);
      selectTypeById.setInt(1, id);
      try (ResultSet rs = selectTypeById.executeQuery()) {
        type = this.typeFactory.getType();
        while (rs.next()) {
          type.setId(rs.getInt("type_id"));
          type.setName(rs.getString("name"));
        }
        return type;
      }
    } catch (

    Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectTypeById");
    }
  }
}
