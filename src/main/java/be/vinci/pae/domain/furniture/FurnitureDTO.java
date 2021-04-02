package be.vinci.pae.domain.furniture;

import be.vinci.pae.domain.picture.PictureDTO;
import be.vinci.pae.domain.type.TypeDTO;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(as = FurnitureImpl.class)
public interface FurnitureDTO {

  int getId();

  void setId(int id);

  String getDescription();

  void setDescription(String description);

  TypeDTO getType();

  void setType(TypeDTO type);

  VisitRequestDTO getVisitRequest();

  void setVisitRequest(VisitRequestDTO visitRequest);

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
