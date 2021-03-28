package be.vinci.pae.domain;

import java.time.LocalDate;
import java.util.List;

public interface FurnitureUCC {

  List<FurnitureDTO> getAllFurniture();

  FurnitureDTO addFurniture(FurnitureDTO furniture);

  List<FurnitureDTO> getFurnitureByTypeName(String typeName);

  List<FurnitureDTO> getFurnitureBySellingPrice(double sellingPrice);

  List<FurnitureDTO> getFurnitureByUserName(String typeName);

  FurnitureDTO modifySellingDate(FurnitureDTO furniture, LocalDate sellingDate);

  FurnitureDTO modifyDepositDate(FurnitureDTO furniture, LocalDate depositDate);

  FurnitureDTO modifyWorkshopDate(FurnitureDTO furniture, LocalDate workshopDate);

}
