package be.vinci.pae.services;

import be.vinci.pae.domain.Address;
import be.vinci.pae.domain.FurnitureDTO;
import be.vinci.pae.domain.VisitRequestDTO;

public interface DAOVisitRequest {

  Address getAddressByUserId(int id);

  String[] getTypesOfFurniture();

  int addPicture(String path);

  int addFurniture(FurnitureDTO furniture);

  int addVisitRequest(VisitRequestDTO visitRequest);

}
