package be.vinci.pae.domain.furniture;

import be.vinci.pae.domain.picture.PictureDTO;
import be.vinci.pae.domain.type.TypeDTO;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestDTO;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import views.Views;

@JsonInclude(JsonInclude.Include.NON_NULL)
class FurnitureImpl implements Furniture {

  // @TODO Pourquoi le format.shape ?
  // Pcq c'est une solution qu'on a vue en backend-java et formater la date dans le frontend était
  // pas si simple que ça (VEVE)

  // Pourquoi pas récup le Timestamp et le formater dans le frontend ? => Plus universel
  // Pourquoi sous le format yyyy-MM-dd et pas dd-MM-yyyy ?
  // Il fallait ce format là pour mettre l'input.value dans un datePicker bootstrap (VEVE)

  // les dates sont toujours sous format yyyy-MM-dd en sql et en java en tout cas initialement...
  // apr�s sit vous d�cidez de modifier �a, why not mais il faut tout changer alors... (LORINE)

  //On abandonne et passe en JS dans le Front End (new Date)

  @JsonView(Views.Public.class)
  private int id;
  @JsonView(Views.Public.class)
  private String description;
  @JsonView(Views.Public.class)
  private TypeDTO type;
  @JsonView(Views.Public.class)
  private VisitRequestDTO visitRequest;
  @JsonView(Views.Public.class)
  private double sellingPrice;
  @JsonView(Views.Public.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date sellingDate;
  @JsonView(Views.Public.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date deliveryDate;
  @JsonView(Views.Public.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date withdrawalDateToCustomer;
  @JsonView(Views.Public.class)
  private UserDTO buyer;
  @JsonView(Views.Public.class)
  private FurnitureCondition condition;
  @JsonView(Views.Public.class)
  private double purchasePrice;

  @JsonView(Views.Admin.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date withdrawalDateFromCustomer;
  @JsonView(Views.Admin.class)
  private double specialSalePrice;
  @JsonView(Views.Admin.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date depositDate;
  @JsonView(Views.Admin.class)
  private String unregisteredBuyerEmail;
  @JsonView(Views.Admin.class)
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

  public TypeDTO getType() {
    return type;
  }

  public void setType(TypeDTO type) {
    this.type = type;
  }

  public VisitRequestDTO getVisitRequest() {
    return visitRequest;
  }

  public void setVisitRequest(VisitRequestDTO visitRequest) {
    this.visitRequest = visitRequest;
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

  public FurnitureCondition getCondition() {
    return condition;
  }

  public void setCondition(FurnitureCondition condition) {
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
