package be.vinci.pae.services;

import be.vinci.pae.domain.Type;

public interface DAOType {

  Type selectTypeByName(String typeName);
  // TODO for later
}
