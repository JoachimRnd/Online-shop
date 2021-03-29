package be.vinci.pae.services;

import java.time.LocalDate;
import java.util.List;
import be.vinci.pae.domain.FurnitureDTO;

public interface DAOFurniture {

  List<FurnitureDTO> selectAllFurniture();

  List<FurnitureDTO> selectFurnituresByType(String type);

  List<FurnitureDTO> selectFurnitureByPrice(double price);

  List<FurnitureDTO> selectFurnitureByUser(String lastNameCustomer);

  FurnitureDTO selectFurnitureById(int id);

  int insertFurniture(FurnitureDTO newFurniture);

  boolean updateSellingDate(int id, LocalDate now);

  boolean updateCondition(int id, String status);

  boolean updateDepositDate(int id, LocalDate now);

}
