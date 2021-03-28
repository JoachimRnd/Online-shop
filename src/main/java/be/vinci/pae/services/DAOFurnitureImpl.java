package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.domain.FurnitureDTO;
import be.vinci.pae.domain.FurnitureFactory;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;

public class DAOFurnitureImpl implements DAOFurniture {

  private PreparedStatement selectAllFurnitures;
  private PreparedStatement selectFurnitureById;
  private PreparedStatement selectFurnitureByType;
  private PreparedStatement selectFurnitureByPrice;
  private PreparedStatement selectFurnitureByUser;

  private PreparedStatement selectTypeId;
  private PreparedStatement selectVisitRequestId;
  private PreparedStatement selectUserId;
  private PreparedStatement selectFavouritePictureId;

  private PreparedStatement insertFurniture;

  private PreparedStatement updateSellingDate;
  private PreparedStatement updateCondition;
  private PreparedStatement updateDepositDate;

  private String querySelectAllFurnitures;
  private String querySelectFurnitureById;
  private String querySelectFurnitureByType;
  private String querySelectFurnitureByPrice;
  private String querySelectFurnitureByUser;

  private String querySelectTypeId;
  private String querySelectVisitRequestId;
  private String querySelectUserId;
  private String querySelectFavouritePictureId;

  private String queryInsertFurniture;

  private String queryUpdateSellingDate;
  private String queryUpdateCondition;
  private String queryUpdateDepositDate;

  @Inject
  private FurnitureFactory furnitureFactory;
  @Inject
  private DalBackendServices dalServices;


  // TODO verifier les sql requests
  /**
   * Contructor of DAOFurnitureImpl. Contain queries.
   */
  public DAOFurnitureImpl() {
    querySelectAllFurnitures = "SELECT f.furniture_id, f.description, f.type,"
        + " f.visit_request, f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer,f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f";
    querySelectFurnitureById = "SELECT f.furniture_id, f.description, f.type, f.visit_request,"
        + " f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer, f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f WHERE f.furniture_id = ?";
    querySelectFurnitureByType = "SELECT f.furniture_id, f.description, f.type, f.visit_request,"
        + " f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer, f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f, project.furniture_type ft"
        + " WHERE ft.type_id = f.type AND ft.name = ?";
    querySelectFurnitureByPrice = "SELECT f.furniture_id, f.description, f.type, f.visit_request,"
        + " f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer, f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f WHERE f.selling_price >= ?";
    querySelectFurnitureByUser = "SELECT f.furniture_id, f.description, f.type, f.visit_request,"
        + " f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer, f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f, project.visit_requests vr,"
        + " project.users u WHERE f.visit_request = vr.visit_request_id AND"
        + " vr.customer = u.user_id AND u.last_name = ?";
    // TODO
    queryInsertFurniture = "";
    queryUpdateSellingDate = "";
    queryUpdateCondition = "";
    queryUpdateDepositDate = "";
  }

  @Override
  public List<FurnitureDTO> selectAllFurnitures() {
    List<FurnitureDTO> listFurnitures = new ArrayList<FurnitureDTO>();
    try {
      selectAllFurnitures = dalServices.getPreparedStatement(querySelectAllFurnitures);
      ResultSet rs = selectAllFurnitures.executeQuery();
      while (rs.next()) {
        FurnitureDTO furniture = furnitureFactory.getFurniture();
        listFurnitures.add(furniture);
      }
      return listFurnitures;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectAllFurnitures");
    }
  }

  // TODO verifier le bon fonctionnement de l'id
  @Override
  public FurnitureDTO selectFurnitureById(int id) {
    FurnitureDTO furniture = null;
    try {
      selectFurnitureById = dalServices.getPreparedStatement(querySelectFurnitureById);
      ResultSet rs = selectFurnitureById.executeQuery();
      if (rs == null)
        furniture = furnitureFactory.getFurniture();
      return furniture;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnitureById");
    }
  }

  @Override
  public List<FurnitureDTO> selectFurnituresByType(String type) {
    List<FurnitureDTO> listFurnitures = new ArrayList<FurnitureDTO>();
    try {
      selectFurnitureByType = dalServices.getPreparedStatement(querySelectFurnitureByType);
      ResultSet rs = selectFurnitureByType.executeQuery();
      while (rs.next()) {
        FurnitureDTO furniture = furnitureFactory.getFurniture();
        listFurnitures.add(furniture);
      }
      return listFurnitures;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnituresByType");
    }
  }

  @Override
  public List<FurnitureDTO> selectFurnitureByPrice(double price) {
    List<FurnitureDTO> listFurnitures = new ArrayList<FurnitureDTO>();
    try {
      selectFurnitureByPrice = dalServices.getPreparedStatement(querySelectFurnitureByPrice);
      ResultSet rs = selectFurnitureByPrice.executeQuery();
      while (rs.next()) {
        FurnitureDTO furniture = furnitureFactory.getFurniture();
        listFurnitures.add(furniture);
      }
      return listFurnitures;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnitureByPrice");
    }
  }

  @Override
  public List<FurnitureDTO> selectFurnitureByUser(String lastNameCustomer) {
    List<FurnitureDTO> listFurnitures = new ArrayList<FurnitureDTO>();
    try {
      selectFurnitureByUser = dalServices.getPreparedStatement(querySelectFurnitureByUser);
      ResultSet rs = selectFurnitureByUser.executeQuery();
      while (rs.next()) {
        FurnitureDTO furniture = furnitureFactory.getFurniture();
        listFurnitures.add(furniture);
      }
      return listFurnitures;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnitureByUser");
    }
  }

  @Override
  public int insertFurniture(FurnitureDTO newFurniture) {
    int furnitureId = -1;
    try {
      // TODO
      PreparedStatement insertFurniture =
          this.dalServices.getPreparedStatement(queryInsertFurniture);
      if (selectTypeId == null && selectVisitRequestId == null && selectUserId == null
          && selectFavouritePictureId == null) {
        selectTypeId = this.dalServices.getPreparedStatement(querySelectTypeId);
        selectVisitRequestId = this.dalServices.getPreparedStatement(querySelectVisitRequestId);
        selectUserId = this.dalServices.getPreparedStatement(querySelectUserId);
        selectFavouritePictureId =
            this.dalServices.getPreparedStatement(querySelectFavouritePictureId);
      }
      selectTypeId.setInt(1, newFurniture.getId());// TODO changer pour typeId

    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : insertFurniture");
    }
    return furnitureId;
  }

  @Override
  public boolean updateSellingDate(int id, LocalDate now) {
    try {
      // TODO
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateSellingDate");
    }
  }

  @Override
  public boolean updateCondition(int id, String status) {
    try {
      // TODO
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateCondition");
    }
  }

  @Override
  public boolean updateDepositDate(int id, LocalDate now) {
    try {
      // TODO
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateDepositDate");
    }
  }


}
