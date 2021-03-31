package be.vinci.pae.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.domain.FurnitureDTO;
import be.vinci.pae.domain.FurnitureFactory;
import be.vinci.pae.utils.FatalException;
import be.vinci.pae.utils.ValueLiaison;
import jakarta.inject.Inject;

public class DAOFurnitureImpl implements DAOFurniture {

  private String querySelectAllFurniture;
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
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


  @Inject
  private FurnitureFactory furnitureFactory;

  @Inject
  private DalBackendServices dalServices;

  @Inject
  private DAOType daoType;

  @Inject
  private DAOVisitRequest daoVisitRequest;

  @Inject
  private DAOUser daoUser;

  // TODO verifier les sql queries

  /**
   * Contructor of DAOFurnitureImpl. Contain queries.
   */
  public DAOFurnitureImpl() {
    querySelectAllFurniture = "SELECT f.furniture_id, f.description, f.type,"
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
        + " f.favourite_picture FROM project.furniture f WHERE f.selling_price = ?";
    querySelectFurnitureByUser = "SELECT f.furniture_id, f.description, f.type, f.visit_request,"
        + " f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer, f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f, project.visit_requests vr,"
        + " project.users u WHERE f.visit_request = vr.visit_request_id AND"
        + " vr.customer = u.user_id AND u.last_name = ?";
    queryInsertFurniture = "INSERT INTO project.furniture (furniture_id, description, type,"
        + " visit_request, purchase_price, withdrawal_date_from_customer, selling_price,"
        + " special_sale_price, deposit_date, selling_date, delivery_date,"
        + " withdrawal_date_to_customer, buyer, condition, unregistered_buyer_email,"
        + " favourite_picture) VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    queryUpdateSellingDate =
        "UPDATE project.furniture SET selling_date = ?::timestamp WHERE furniture_id = ?";
    queryUpdateCondition = "UPDATE project.furniture SET condition = ? WHERE furniture_id = ?";
    queryUpdateDepositDate =
        "UPDATE project.furniture SET deposit_date = ?::timestamp WHERE furniture_id = ?";

  }

  @Override
  public List<FurnitureDTO> selectAllFurniture() {
    try {
      PreparedStatement selectAllFurniture =
          dalServices.getPreparedStatement(querySelectAllFurniture);
      List<FurnitureDTO> listFurniture = new ArrayList<>();
      try (ResultSet rs = selectAllFurniture.executeQuery()) {
        FurnitureDTO furniture;
        do {
          furniture = createFurniture(rs);
          listFurniture.add(furniture);
        } while (furniture != null);
        listFurniture.remove(listFurniture.size() - 1);
      }
      return listFurniture;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectAllFurnitures");
    }
  }

  @Override
  public FurnitureDTO selectFurnitureById(int id) {
    FurnitureDTO furniture = null;
    try {
      PreparedStatement selectFurnitureById =
          dalServices.getPreparedStatement(querySelectFurnitureById);
      selectFurnitureById.setInt(1, id);
      try (ResultSet rs = selectFurnitureById.executeQuery()) {
        furniture = createFurniture(rs);
        return furniture;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnitureById");
    }
  }

  private FurnitureDTO createFurniture(ResultSet rs) throws SQLException {
    FurnitureDTO furniture = null;
    if (rs.next()) {
      furniture = this.furnitureFactory.getFurniture();
      furniture.setId(rs.getInt("furniture_id"));
      furniture.setDescription(rs.getString("description"));
      furniture.setType(this.daoType.selectTypeById(rs.getInt("type")));
      furniture
          .setVisitRequest(this.daoVisitRequest.selectVisitRequestById(rs.getInt("visit_request")));
      furniture.setPurchasePrice(rs.getDouble("purchase_price"));
      furniture.setWithdrawalDateFromCustomer(rs.getDate("withdrawal_date_from_customer"));
      furniture.setSellingPrice(rs.getDouble("selling_price"));
      furniture.setSpecialSalePrice(rs.getDouble("special_sale_price"));
      furniture.setDepositDate(rs.getDate("deposit_date"));
      furniture.setSellingDate(rs.getDate("selling_date"));
      furniture.setDeliveryDate(rs.getDate("delivery_date"));
      furniture.setWithdrawalDateToCustomer(rs.getDate("withdrawal_date_to_customer"));
      furniture.setBuyer(this.daoUser.getUserById(rs.getInt("buyer")));
      furniture.setCondition(ValueLiaison.intToStringCondition(rs.getInt("condition")));
      furniture.setUnregisteredBuyerEmail(rs.getString("unregistered_buyer_email"));
    }
    return furniture;
  }


  // TODO
  @Override
  public List<FurnitureDTO> selectFurnitureByType(String type) {
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    try {
      PreparedStatement selectFurnitureByType =
          dalServices.getPreparedStatement(querySelectFurnitureByType);
      selectFurnitureByType.setString(1, type);
      ResultSet rs = selectFurnitureByType.executeQuery();
      while (rs.next()) {
        FurnitureDTO furniture = furnitureFactory.getFurniture();
        listFurniture.add(furniture);
      }
      return listFurniture;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnitureByType");
    }
  }

  // TODO
  @Override
  public List<FurnitureDTO> selectFurnitureByPrice(double price) {
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    try {
      PreparedStatement selectFurnitureByPrice =
          dalServices.getPreparedStatement(querySelectFurnitureByPrice);
      selectFurnitureByPrice.setDouble(1, price);
      ResultSet rs = selectFurnitureByPrice.executeQuery();
      while (rs.next()) {
        FurnitureDTO furniture = furnitureFactory.getFurniture();
        listFurniture.add(furniture);
      }
      return listFurniture;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnitureByPrice");
    }
  }

  // TODO
  @Override
  public List<FurnitureDTO> selectFurnitureByUser(String lastNameCustomer) {
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    try {
      PreparedStatement selectFurnitureByUser =
          dalServices.getPreparedStatement(querySelectFurnitureByUser);
      ResultSet rs = selectFurnitureByUser.executeQuery();
      while (rs.next()) {
        FurnitureDTO furniture = furnitureFactory.getFurniture();
        listFurniture.add(furniture);
      }
      return listFurniture;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnitureByUser");
    }
  }

  // TODO
  @Override
  public int insertFurniture(FurnitureDTO newFurniture) {
    int furnitureId = -1;
    try {
      PreparedStatement insertFurniture =
          this.dalServices.getPreparedStatement(queryInsertFurniture);
      PreparedStatement selectTypeId = this.dalServices.getPreparedStatement(querySelectTypeId);
      selectTypeId.setInt(1, newFurniture.getType().getId());
      PreparedStatement selectVisitRequestId =
          this.dalServices.getPreparedStatement(querySelectVisitRequestId);
      selectVisitRequestId.setInt(1, newFurniture.getVisitRequest().getId());
      PreparedStatement selectUserId = this.dalServices.getPreparedStatement(querySelectUserId);
      selectUserId.setInt(1, newFurniture.getBuyer().getId());
      PreparedStatement selectFavouritePictureId =
          this.dalServices.getPreparedStatement(querySelectFavouritePictureId);
      selectFavouritePictureId.setInt(1, newFurniture.getFavouritePicture().getId());
      ResultSet rsType = selectTypeId.executeQuery();
      ResultSet rsVisitRequest = selectVisitRequestId.executeQuery();
      ResultSet rsUser = selectUserId.executeQuery();
      ResultSet rsFavouritePicture = selectFavouritePictureId.executeQuery();
      if (rsType != null && rsVisitRequest != null && rsUser != null
          && rsFavouritePicture != null) {
        insertFurniture.setString(1, newFurniture.getDescription());
        insertFurniture.setInt(2, newFurniture.getType().getId());
        insertFurniture.setInt(3, newFurniture.getVisitRequest().getId());
        insertFurniture.setDouble(4, newFurniture.getPurchasePrice());
        insertFurniture.setDate(5, (Date) newFurniture.getWithdrawalDateFromCustomer());
        insertFurniture.setDouble(6, newFurniture.getSellingPrice());
        insertFurniture.setDouble(7, newFurniture.getSpecialSalePrice());
        insertFurniture.setDate(8, (Date) newFurniture.getDepositDate());
        insertFurniture.setDate(9, (Date) newFurniture.getSellingDate());
        insertFurniture.setDate(10, (Date) newFurniture.getDeliveryDate());
        insertFurniture.setDate(11, (Date) newFurniture.getWithdrawalDateToCustomer());
        insertFurniture.setInt(12, newFurniture.getBuyer().getId());
        insertFurniture.setString(13, newFurniture.getCondition());
        insertFurniture.setString(14, newFurniture.getUnregisteredBuyerEmail());
        insertFurniture.setInt(15, newFurniture.getFavouritePicture().getId());
        insertFurniture.execute();
      } else {
        throw new FatalException("there is no typeId or no visiteRequestId or userId or"
            + " favouritePictureId as mentionned");
      }
      try (ResultSet rs = insertFurniture.getGeneratedKeys()) {
        if (rs.next()) {
          furnitureId = rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : insertFurniture");
    }
    return furnitureId;
  }


  @Override
  public boolean updateSellingDate(int id, LocalDateTime now) {
    try {
      PreparedStatement updateSellingDate =
          this.dalServices.getPreparedStatement(queryUpdateSellingDate);
      String date = now.format(formatter);
      updateSellingDate.setString(1, date);
      updateSellingDate.setInt(2, id);
      return updateSellingDate.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateSellingDate");
    }
  }

  @Override
  public boolean updateCondition(int id, String status) {
    try {
      PreparedStatement updateCondition =
          this.dalServices.getPreparedStatement(queryUpdateCondition);
      updateCondition.setInt(1, ValueLiaison.stringToIntCondition(status));
      updateCondition.setInt(2, id);
      return updateCondition.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateCondition");
    }
  }

  @Override
  public boolean updateDepositDate(int id, LocalDateTime now) {
    try {
      PreparedStatement updateDepositDate =
          this.dalServices.getPreparedStatement(queryUpdateDepositDate);
      updateDepositDate.setString(1, now.format(formatter));
      updateDepositDate.setInt(2, id);
      return updateDepositDate.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateDepositDate");
    }
  }


}
