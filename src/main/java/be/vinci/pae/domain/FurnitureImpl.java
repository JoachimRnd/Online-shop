package be.vinci.pae.domain;

import java.time.LocalDateTime;

class FurnitureImpl implements Furniture {

  private int id;
  private String description;
  private String type;
  private VisiteRequestDTO visiteRequest;
  private double purchasePrice;
  private LocalDateTime withdrawalDateFromCustomer;
  private double sellingPrice;
  private double specialSalePrice;
  private LocalDateTime depositDate;
  private LocalDateTime sellingDate;
  private LocalDateTime deliveryDate;
  private LocalDateTime withdrawalDateToCustomer;
  private String buyer;
  private String condition;
  private String unregisteredBuyerEmail;
  private Picture favouritePicture;

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public VisiteRequestDTO getVisiteRequest() {
    return visiteRequest;
  }

  public double getPurchasePrice() {
    return purchasePrice;
  }

  public LocalDateTime getWithdrawalDateFromCustomer() {
    return withdrawalDateFromCustomer;
  }

  public double getSellingPrice() {
    return sellingPrice;
  }

  public double getSpecialSalePrice() {
    return specialSalePrice;
  }

  public LocalDateTime getDepositDate() {
    return depositDate;
  }

  public LocalDateTime getSellingDate() {
    return sellingDate;
  }

  public LocalDateTime getDeliveryDate() {
    return deliveryDate;
  }

  public LocalDateTime getWithdrawalDateToCustomer() {
    return withdrawalDateToCustomer;
  }

  public String getBuyer() {
    return buyer;
  }

  public String getCondition() {
    return condition;
  }

  public String getUnregisteredBuyerEmail() {
    return unregisteredBuyerEmail;
  }

  public Picture getFavouritePicture() {
    return favouritePicture;
  }


  @Override
  public void setId(int id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setVisiteRequest(VisiteRequestDTO visiteRequest) {
    this.visiteRequest = visiteRequest;
  }

  public void setPurchasePrice(double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public void setWithdrawalDateFromCustomer(LocalDateTime withdrawalDateFromCustomer) {
    this.withdrawalDateFromCustomer = withdrawalDateFromCustomer;
  }

  public void setSellingPrice(double sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public void setSpecialSalePrice(double specialSalePrice) {
    this.specialSalePrice = specialSalePrice;
  }

  public void setDepositDate(LocalDateTime depositDate) {
    this.depositDate = depositDate;
  }

  public void setSellingDate(LocalDateTime sellingDate) {
    this.sellingDate = sellingDate;
  }

  public void setDeliveryDate(LocalDateTime deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public void setWithdrawalDateToCustomer(LocalDateTime withdrawalDateToCustomer) {
    this.withdrawalDateToCustomer = withdrawalDateToCustomer;
  }

  public void setBuyer(String buyer) {
    this.buyer = buyer;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public void setUnregisteredBuyerEmail(String unregisteredBuyerEmail) {
    this.unregisteredBuyerEmail = unregisteredBuyerEmail;
  }

  public void setFavouritePicture(Picture favouritePicture) {
    this.favouritePicture = favouritePicture;
  }


}
