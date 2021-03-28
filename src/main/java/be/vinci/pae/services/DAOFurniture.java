package be.vinci.pae.services;

import java.util.List;
import be.vinci.pae.domain.FurnitureDTO;

public interface DAOFurniture {

  List<FurnitureDTO> selectAllFurnitures();

  List<FurnitureDTO> selectFurnituresByType(String type);

  List<FurnitureDTO> selectFurnitureByPrice(double price);

  List<FurnitureDTO> selectFurnitureByUser(String lastNameCustomer);

  FurnitureDTO selectFurnitureById(int id);

}
