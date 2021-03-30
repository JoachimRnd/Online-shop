package be.vinci.pae.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import views.Views;

@JsonInclude(JsonInclude.Include.NON_NULL)
class FurnitureImpl implements Furniture {

  @JsonView(Views.Public.class)
  private int id;

  @JsonView(Views.Public.class)
  private String description;

  @JsonView(Views.Public.class)
  private String type;

  @JsonView(Views.Public.class)
  private VisitRequestDTO visitRequest;

  @JsonView(Views.Public.class)
  private double purchasePrice;

  @JsonView(Views.Public.class)
  private Date withdrawalDateFromCustomer;

  @JsonView(Views.Public.class)
  private double sellingPrice;

  @JsonView(Views.Public.class)
  private double specialSalePrice;

  @JsonView(Views.Public.class)
  private Date depositDate;

  @JsonView(Views.Public.class)
  private Date sellingDate;

  @JsonView(Views.Public.class)
  private Date deliveryDate;

  @JsonView(Views.Public.class)
  private Date withdrawalDateToCustomer;

  @JsonView(Views.Public.class)
  private UserDTO buyer;

  @JsonView(Views.Public.class)
  private String condition;

  @JsonView(Views.Public.class)
  private String unregisteredBuyerEmail;

  @JsonView(Views.Public.class)
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
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
