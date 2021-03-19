package be.vinci.pae.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = FurnitureImpl.class)
public interface FurnitureDTO {

  int getId();

  String getDescription();

  VisiteRequestDTO getVisiteRequest();

  double getPuchasePrice();

  LocalDateTime getWithdrawalDateFromCustomer();

  double getSellingPrice();

  double getSpecialSalePrice();

  LocalDateTime getDepositDate();

  LocalDateTime getSellingDate();

  LocalDateTime getDeliveryDate();

  LocalDateTime getWithdrawalDateToCustomer();

  String getBuyer();

  String getCondition();

  String getUnregisteredBuyerEmail();

  Picture getFavouritePicture();


  void setId(int id);

  void setDescription(String description);

  void setVisiteRequest(VisiteRequestDTO visiteRequest);

  void setPurchasePrice(double purchasePrice);

  void setWithdrawalDateFromCustomer(LocalDateTime withdrawalDateFromCustomer);

  void setSellingPrice(double sellingPrice);

  void setSpecialSalePrice(double specialSalePrice);

  void setDepositDate(LocalDateTime depositDate);

  void setSellingDate(LocalDateTime sellingDate);

  void setDeliveryDate(LocalDateTime deliveryDate);

  void setWithdrawalDateToCustomer(LocalDateTime withdrawalDateToCustomer);

  void setBuyer(String buyer);

  void setCondition(String condition);

  void setUnregisteredBuyerEmail(String unregisteredBuyerEmail);

  void setFavouritePicture(Picture favouritePicture);


}
