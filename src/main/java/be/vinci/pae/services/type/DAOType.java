package be.vinci.pae.services.type;

import be.vinci.pae.domain.type.TypeDTO;

public interface DAOType {

  TypeDTO selectTypeByName(String typeName);

  TypeDTO selectTypeById(int id);

  int addType(TypeDTO type);
}
