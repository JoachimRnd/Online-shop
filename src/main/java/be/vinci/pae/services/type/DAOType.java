package be.vinci.pae.services.type;

import be.vinci.pae.domain.type.TypeDTO;
import java.util.List;

public interface DAOType {
  //@TODO Methodes inutilisée => Supprimer ?

  List<TypeDTO> selectFurnitureTypes();

  TypeDTO selectTypeByName(String typeName);

  TypeDTO selectTypeById(int id);

  int addType(String type);

  boolean deleteFurnitureType(int id);

  List<String> getAllTypesNames();
}
