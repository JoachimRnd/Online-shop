package be.vinci.pae.services.furniture;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import be.vinci.pae.domain.furniture.FurnitureDTO;

public interface DAOFurniture {

  List<FurnitureDTO> selectAllFurniture();

  List<FurnitureDTO> selectFurnitureUsers(int idUser);

  List<FurnitureDTO> selectFurnitureByType(String type);

  List<FurnitureDTO> selectFurnitureByPrice(double price);

  List<FurnitureDTO> selectFurnitureByUser(String lastNameCustomer);

  FurnitureDTO selectFurnitureById(int id);

  int insertFurniture(FurnitureDTO newFurniture);

  boolean updateSellingDate(int id, Instant now);

  boolean updateCondition(int id, int condition);

  boolean updateDepositDate(int id, Instant now);

  boolean updateSellingPrice(int id, Double price);

  boolean updateDescription(int id, String description);

  List<FurnitureDTO> selectSalesFurniture();

  boolean updateType(int furnitureId, int typeId);

  boolean updatePurchasePrice(int id, Double price);

  boolean updateWithdrawalDateToCustomer(int id, LocalDate now);

  boolean updateWithdrawalDateFromCustomer(int id, LocalDate now);

  boolean updateDeliveryDate(int id, LocalDate now);

  boolean updateSpecialSalePrice(int id, Double price);

  boolean updateUnregisteredBuyerEmail(int id, String buyer);

  boolean updateBuyer(int id, int buyerId);

  boolean updateFavouritePicture(int id, int pictureId);


}
