package be.vinci.pae.services.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.domain.type.TypeDTO;
import be.vinci.pae.domain.type.TypeFactory;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;

public class DAOTypeImpl implements DAOType {

  private String querySelectTypeId;
  private String querySelectAllTypes;

  @Inject
  private TypeFactory typeFactory;

  @Inject
  private DalBackendServices dalServices;

  /**
   * Constructor of the DAO.
   */
  public DAOTypeImpl() {
    querySelectTypeId =
        "SELECT t.type_id, t.name FROM project.furniture_types t WHERE t.type_id = ?";
    querySelectAllTypes = "SELECT t.type_id, t.name FROM project.furniture_types t";
  }

  @Override
  public List<TypeDTO> selectFurnitureTypes() {
    try {
      PreparedStatement selectFurnitureTypes =
          dalServices.getPreparedStatement(querySelectAllTypes);
      try (ResultSet rs = selectFurnitureTypes.executeQuery()) {
        List<TypeDTO> listTypes = new ArrayList<>();
        TypeDTO type;
        do {
          type = createType(rs);
          listTypes.add(type);
        } while (type != null);
        listTypes.remove(listTypes.size() - 1);
        return listTypes;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectAllTypes");
    }
  }

  @Override
  public TypeDTO selectTypeByName(String typeName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TypeDTO selectTypeById(int id) {
    try {
      PreparedStatement selectTypeById = dalServices.getPreparedStatement(querySelectTypeId);
      selectTypeById.setInt(1, id);
      try (ResultSet rs = selectTypeById.executeQuery()) {
        return createType(rs);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectTypeById");
    }
  }

  @Override
  public int addType(TypeDTO type) {
    // TODO Auto-generated method stub
    return -1;
  }

  private TypeDTO createType(ResultSet rs) throws SQLException {
    TypeDTO type = null;
    if (rs.next()) {
      type = typeFactory.getType();
      type.setId(rs.getInt("type_id"));
      type.setName(rs.getString("name"));
    }
    return type;
  }
}
