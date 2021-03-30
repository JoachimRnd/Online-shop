package be.vinci.pae.services;

import be.vinci.pae.domain.TypeDTO;

public interface DAOType {

  TypeDTO selectTypeByName(String typeName);

  TypeDTO selectTypeById(int id);
  // TODO for later
}
