package be.vinci.pae.services;

import java.time.LocalDateTime;
import java.util.List;
import be.vinci.pae.domain.FurnitureDTO;

public interface DAOFurniture {

  List<FurnitureDTO> selectAllFurniture();

  List<FurnitureDTO> selectFurnitureByType(String type);

  List<FurnitureDTO> selectFurnitureByPrice(double price);

  List<FurnitureDTO> selectFurnitureByUser(String lastNameCustomer);

  FurnitureDTO selectFurnitureById(int id);

  int insertFurniture(FurnitureDTO newFurniture);

  boolean updateSellingDate(int id, LocalDateTime now);

  boolean updateCondition(int id, String status);

  boolean updateDepositDate(int id, LocalDateTime now);

}
