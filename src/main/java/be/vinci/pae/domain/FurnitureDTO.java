package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(as = FurnitureImpl.class)
public interface FurnitureDTO {

  int getId();

  void setId(int id);

  String getDescription();

  void setDescription(String description);

  VisitRequestDTO getVisiteRequest();

  void setVisiteRequest(VisitRequestDTO visiteRequest);

  double getPurchasePrice();

  void setPurchasePrice(double purchasePrice);

  Date getWithdrawalDateFromCustomer();

  void setWithdrawalDateFromCustomer(Date withdrawalDateFromCustomer);

  double getSellingPrice();

  void setSellingPrice(double sellingPrice);

  double getSpecialSalePrice();

  void setSpecialSalePrice(double specialSalePrice);

  Date getDepositDate();

  void setDepositDate(Date depositDate);

  Date getSellingDate();

  void setSellingDate(Date sellingDate);

  Date getDeliveryDate();

  void setDeliveryDate(Date deliveryDate);

  Date getWithdrawalDateToCustomer();

  void setWithdrawalDateToCustomer(Date withdrawalDateToCustomer);

  UserDTO getBuyer();

  void setBuyer(UserDTO buyer);

  String getCondition();

  void setCondition(String condition);

  String getUnregisteredBuyerEmail();

  void setUnregisteredBuyerEmail(String unregisteredBuyerEmail);

  PictureDTO getFavouritePicture();

  void setFavouritePicture(PictureDTO favouritePicture);

}
