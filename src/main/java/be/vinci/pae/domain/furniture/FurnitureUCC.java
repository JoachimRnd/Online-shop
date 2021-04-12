package be.vinci.pae.domain.furniture;

import java.time.Instant;
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

  boolean modifyType(int furnitureId, int typeId);

  boolean modifyPurchasePrice(int id, double price);

  boolean modifyWithdrawalDate(int id, Instant time);

  boolean modifyDescription(int id, String description);

  boolean modifyBuyerEmail(int id, String email);

  FurnitureDTO getFurnitureById(int id);

  List<FurnitureDTO> getSalesFurnitureAdmin();


}
