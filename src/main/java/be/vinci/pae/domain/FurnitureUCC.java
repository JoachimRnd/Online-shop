package be.vinci.pae.domain;

import java.util.List;

public interface FurnitureUCC {

  List<FurnitureDTO> getAllFurniture();

  FurnitureDTO addFurniture(FurnitureDTO furniture);

  List<FurnitureDTO> getFurnitureByTypeName(String typeName);

  List<FurnitureDTO> getFurnitureBySellingPrice(double sellingPrice);

  List<FurnitureDTO> getFurnitureByUserName(String typeName);

  boolean modifyCondition(int id, String condition, double price);

  FurnitureDTO modifySellingDate(FurnitureDTO furniture, String status);

  FurnitureDTO modifyDepositDate(FurnitureDTO furniture, String status);

  FurnitureDTO modifyWorkshopDate(FurnitureDTO furniture, String status);

  FurnitureDTO getFurnitureById(int id);

}
