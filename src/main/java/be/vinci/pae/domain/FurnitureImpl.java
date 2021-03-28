package be.vinci.pae.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
class FurnitureImpl implements Furniture {

  private int id;
  private String description;
  private Type type;
  private VisitRequestDTO visitRequest;
  private double purchasePrice;
  private Date withdrawalDateFromCustomer;
  private double sellingPrice;
  private double specialSalePrice;
  private Date depositDate;
  private Date sellingDate;
  private Date deliveryDate;
  private Date withdrawalDateToCustomer;
  private UserDTO buyer;
  private String condition;
  private String unregisteredBuyerEmail;
  private PictureDTO favouritePicture;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public VisitRequestDTO getVisiteRequest() {
    return visitRequest;
  }

  public void setVisiteRequest(VisitRequestDTO visiteRequest) {
    this.visitRequest = visiteRequest;
  }

  public double getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public Date getWithdrawalDateFromCustomer() {
    return withdrawalDateFromCustomer;
  }

  public void setWithdrawalDateFromCustomer(Date withdrawalDateFromCustomer) {
    this.withdrawalDateFromCustomer = withdrawalDateFromCustomer;
  }

  public double getSellingPrice() {
    return sellingPrice;
  }

  public void setSellingPrice(double sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public double getSpecialSalePrice() {
    return specialSalePrice;
  }

  public void setSpecialSalePrice(double specialSalePrice) {
    this.specialSalePrice = specialSalePrice;
  }

  public Date getDepositDate() {
    return depositDate;
  }

  public void setDepositDate(Date depositDate) {
    this.depositDate = depositDate;
  }

  public Date getSellingDate() {
    return sellingDate;
  }

  public void setSellingDate(Date sellingDate) {
    this.sellingDate = sellingDate;
  }

  public Date getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(Date deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public Date getWithdrawalDateToCustomer() {
    return withdrawalDateToCustomer;
  }

  public void setWithdrawalDateToCustomer(Date withdrawalDateToCustomer) {
    this.withdrawalDateToCustomer = withdrawalDateToCustomer;
  }

  public UserDTO getBuyer() {
    return buyer;
  }

  public void setBuyer(UserDTO buyer) {
    this.buyer = buyer;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public String getUnregisteredBuyerEmail() {
    return unregisteredBuyerEmail;
  }

  public void setUnregisteredBuyerEmail(String unregisteredBuyerEmail) {
    this.unregisteredBuyerEmail = unregisteredBuyerEmail;
  }

  public PictureDTO getFavouritePicture() {
    return favouritePicture;
  }

  public void setFavouritePicture(PictureDTO favouritePicture) {
    this.favouritePicture = favouritePicture;
  }


}
