package be.vinci.pae.services;

import be.vinci.pae.domain.AddressDTO;
import be.vinci.pae.domain.FurnitureDTO;
import be.vinci.pae.domain.VisitRequestDTO;

public interface DAOVisitRequest {

  AddressDTO getAddressByUserId(int id);

  String[] getTypesOfFurniture();

  int addPicture(String path);

  int addFurniture(FurnitureDTO furniture);

  int addVisitRequest(VisitRequestDTO visitRequest);

}
