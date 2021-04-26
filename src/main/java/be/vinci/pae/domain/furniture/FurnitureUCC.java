package be.vinci.pae.domain.furniture;

import java.time.LocalDate;
import java.util.List;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import java.util.List;

public interface FurnitureUCC {

  List<FurnitureDTO> getAllFurniture();

  List<FurnitureDTO> getFurnitureUsers(int idUser);

  FurnitureDTO addFurniture(FurnitureDTO furniture);

  List<FurnitureDTO> getFurnitureByTypeName(String typeName);

  List<FurnitureDTO> getFurnitureBySellingPrice(double sellingPrice);

  List<FurnitureDTO> getFurnitureByUserName(String typeName);

  boolean modifyCondition(int id, FurnitureCondition condition);

  boolean modifyType(int furnitureId, int typeId);

  boolean modifyPurchasePrice(int id, double price);

  boolean modifyDescription(int id, String description);

  boolean modifyBuyerEmail(int id, String email);

  FurnitureDTO getFurnitureById(int id);

  List<FurnitureDTO> getSalesFurnitureAdmin();

  boolean modifyDeliveryDate(int id, LocalDate time);

  boolean modifyWithdrawalDateFromCustomer(int id, LocalDate time);

  boolean modifyWithdrawalDateToCustomer(int id, LocalDate time);

  boolean modifySellingPrice(int id, double price);

  boolean modifySpecialSalePrice(int id, double price);

  boolean modifyFavouritePicture(int id, int pictureId);


  List<FurnitureDTO> getFurnituresFiltered(String type, double price, String username);

  List<FurnitureDTO> getFurnitureBuyBy(int id);

  List<FurnitureDTO> getFurnitureSellBy(int id);
}
