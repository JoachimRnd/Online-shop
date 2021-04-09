package be.vinci.pae.services.type;

import java.util.List;
import be.vinci.pae.domain.type.TypeDTO;

public interface DAOType {

  List<TypeDTO> selectFurnitureTypes();

  TypeDTO selectTypeByName(String typeName);

  TypeDTO selectTypeById(int id);

  int addType(TypeDTO type);

}
