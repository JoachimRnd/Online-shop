package be.vinci.pae.domain.furniture;

import java.util.List;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;

public interface FurnitureUCC {

  List<FurnitureDTO> getAllFurniture();

  List<FurnitureDTO> getFurnitureUsers();

  FurnitureDTO addFurniture(FurnitureDTO furniture);

  List<FurnitureDTO> getFurnitureByTypeName(String typeName);

  List<FurnitureDTO> getFurnitureBySellingPrice(double sellingPrice);

  List<FurnitureDTO> getFurnitureByUserName(String typeName);

  boolean modifyCondition(int id, FurnitureCondition condition, double price);

  FurnitureDTO getFurnitureById(int id);

  List<FurnitureDTO> getSalesFurnitureAdmin();


}
