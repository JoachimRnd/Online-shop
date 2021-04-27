package be.vinci.pae.services.type;

import java.util.List;
import be.vinci.pae.domain.type.TypeDTO;

public interface DAOType {
  // @TODO Methodes inutilisée => Supprimer ?
  // Yes on peut
  // yep
  List<TypeDTO> selectFurnitureTypes();

  TypeDTO selectTypeByName(String typeName);

  TypeDTO selectTypeById(int id);

  int addType(String type);

  boolean deleteFurnitureType(int id);

  List<String> getAllTypesNames();
}
