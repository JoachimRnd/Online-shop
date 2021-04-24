package be.vinci.pae.domain.furniture;

import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import java.util.List;

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


  List<FurnitureDTO> getFurnituresFiltered(String type, double price, String username);

  List<FurnitureDTO> getFurnitureBuyBy(int id);

  List<FurnitureDTO> getFurnitureSellBy(int id);
}
