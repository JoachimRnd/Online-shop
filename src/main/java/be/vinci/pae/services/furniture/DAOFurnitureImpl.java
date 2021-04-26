package be.vinci.pae.services.furniture;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.domain.furniture.FurnitureDTO;
import be.vinci.pae.domain.furniture.FurnitureFactory;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.services.type.DAOType;
import be.vinci.pae.services.user.DAOUser;
import be.vinci.pae.services.visitrequest.DAOVisitRequest;
import be.vinci.pae.utils.FatalException;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import be.vinci.pae.utils.ValueLink.OptionStatus;
import jakarta.inject.Inject;

public class DAOFurnitureImpl implements DAOFurniture {

  private String querySelectAllFurniture;
  private String querySelectFurnitureUser;
  private String querySelectFurnitureById;
  private String queryInsertFurniture;
  private String queryUpdateSellingDate;
  private String queryUpdateCondition;
  private String queryUpdateDepositDate;
  private String queryUpdateSellingPrice;
  private String queryUpdateSpecialSalePrice;
  private String querySelectSalesFurniture;
  private String querySelectFurnituresFiltered;
  private String querySelectFurnitureBuyBy;
  private String querySelectFurnitureSellBy;
  private String queryUpdateType;
  private String queryUpdatePurchasePrice;
  private String queryUpdateDescription;
  private String queryUpdateWithdrawalDateToCustomer;
  private String queryUpdateWithdrawalDateFromCustomer;
  private String queryUpdateDeliveryDate;
  private String queryUpdateUnregisteredBuyerEmail;
  private String queryUpdateBuyer;
  private String queryUpdateFavouritePicture;
  private String querySelectFurnituresInOptionUser;
  private String querySelectFurnitureByFavouritePicture;

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

  /*
   * querySelectFurnitureByType = "SELECT f.furniture_id, f.description, f.type, f.visit_request,"
   * +" f.purchase_price, f.withdrawal_date_from_customer, f.selling_price," + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
   * + " f.withdrawal_date_to_customer, f.buyer, f.condition, f.unregistered_buyer_email," +
   * " f.favourite_picture FROM project.furniture f, project.furniture_type ft" + " WHERE ft.type_id = f.type AND ft.name = ?";
   * querySelectFurnitureByPrice = "SELECT f.furniture_id, f.description, f.type, f.visit_request,"
   * +" f.purchase_price, f.withdrawal_date_from_customer, f.selling_price," + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
   * + " f.withdrawal_date_to_customer, f.buyer, f.condition, f.unregistered_buyer_email,"
   * +" f.favourite_picture FROM project.furniture f WHERE f.selling_price = ?" ; querySelectFurnitureByUser =
   * "SELECT f.furniture_id, f.description, f.type, f.visit_request," + " f.purchase_price, f.withdrawal_date_from_customer, f.selling_price," +
   * " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date," +
   * " f.withdrawal_date_to_customer, f.buyer, f.condition, f.unregistered_buyer_email," +
   * " f.favourite_picture FROM project.furniture f, project.visit_requests vr," + " project.users u WHERE f.visit_request = vr.visit_request_id AND" +
   * " vr.customer = u.user_id AND u.last_name = ?";
   */

  /**
   * Contructor of DAOFurnitureImpl. Contain queries.
   */
  public DAOFurnitureImpl() {
    querySelectAllFurniture = "SELECT f.furniture_id, f.description, f.type,"
        + " f.visit_request, f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer,f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f";
    querySelectFurnitureUser = "SELECT f.furniture_id, f.description, f.type,"
        + " f.visit_request, f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer,f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f WHERE f.condition = ?";
    querySelectFurnitureById = "SELECT f.furniture_id, f.description, f.type, f.visit_request,"
        + " f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer, f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f WHERE f.furniture_id = ?";
    queryInsertFurniture = "INSERT INTO project.furniture (furniture_id, description, type,"
        + " visit_request, purchase_price, withdrawal_date_from_customer, selling_price,"
        + " special_sale_price, deposit_date, selling_date, delivery_date,"
        + " withdrawal_date_to_customer, buyer, condition, unregistered_buyer_email,"
        + " favourite_picture) VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    queryUpdateSellingDate = "UPDATE project.furniture SET selling_date = ? WHERE furniture_id = ?";
    queryUpdateCondition = "UPDATE project.furniture SET condition = ? WHERE furniture_id = ?";
    queryUpdateDepositDate = "UPDATE project.furniture SET deposit_date = ? WHERE furniture_id = ?";
    queryUpdateSellingPrice =
        "UPDATE project.furniture SET selling_price = ? WHERE furniture_id = ?";
    queryUpdateSpecialSalePrice =
        "UPDATE project.furniture SET special_sale_price = ? WHERE furniture_id = ?";
    querySelectSalesFurniture = "SELECT f.furniture_id, f.description, f.type,"
        + " f.visit_request, f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer,f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f "
        + " WHERE f.condition = 'vendu' OR f.condition = 'propose'";
    querySelectFurnituresFiltered = "SELECT DISTINCT f.furniture_id, f.description, f.type,"
        + " f.visit_request, f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer,f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f, project.furniture_types t, "
        + "project.users u, project.visit_requests v WHERE lower(t.name) LIKE lower(?) AND "
        + "(f.purchase_price < ? OR f.selling_price < ?) AND lower(u.username) LIKE lower(?) "
        + "AND f.type = t.type_id AND (f.buyer = u.user_id OR "
        + "(v.customer = u.user_id AND v.visit_request_id = f.visit_request))";
    querySelectFurnitureBuyBy = "SELECT f.furniture_id, f.description, f.type,"
        + " f.visit_request, f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer,f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f WHERE f.buyer = ?";
    querySelectFurnitureSellBy = "SELECT f.furniture_id, f.description, f.type,"
        + " f.visit_request, f.purchase_price, f.withdrawal_date_from_customer, f.selling_price,"
        + " f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date,"
        + " f.withdrawal_date_to_customer, f.buyer,f.condition, f.unregistered_buyer_email,"
        + " f.favourite_picture FROM project.furniture f, project.visit_requests v"
        + " WHERE f.visit_request = v.visit_request_id AND v.customer = ?";
    queryUpdateType = "UPDATE project.furniture SET type = ? WHERE furniture_id = ?";
    queryUpdatePurchasePrice =
        "UPDATE project.furniture SET purchase_price = ? WHERE furniture_id = ?";
    queryUpdateDescription = "UPDATE project.furniture SET description = ? WHERE furniture_id = ?";
    queryUpdateWithdrawalDateToCustomer =
        "UPDATE project.furniture SET withdrawal_date_to_customer = ? WHERE furniture_id = ?";
    queryUpdateWithdrawalDateFromCustomer =
        "UPDATE project.furniture SET withdrawal_date_from_customer = ? WHERE furniture_id = ?";
    queryUpdateDeliveryDate =
        "UPDATE project.furniture SET delivery_date = ? WHERE furniture_id = ?";
    queryUpdateUnregisteredBuyerEmail =
        "UPDATE project.furniture SET unregistered_buyer_email = ? WHERE furniture_id = ?";
    queryUpdateBuyer = "UPDATE project.furniture SET buyer = ? WHERE furniture_id = ?";
    queryUpdateFavouritePicture =
        "UPDATE project.furniture SET favourite_picture = ? WHERE furniture_id = ?";
    querySelectFurnituresInOptionUser =
        "SELECT f.furniture_id, f.description, f.type, f.visit_request, f.purchase_price, f.withdrawal_date_from_customer, "
            + "f.selling_price, f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date, f.withdrawal_date_to_customer, "
            + "f.buyer,f.condition, f.unregistered_buyer_email, f.favourite_picture FROM project.furniture f, project.options o "
            + "WHERE o.furniture = f.furniture_id AND f.condition = ? AND o.status = ? AND o.buyer = ?;";
    querySelectFurnitureByFavouritePicture =
        "SELECT f.furniture_id, f.description, f.type, f.visit_request, f.purchase_price, f.withdrawal_date_from_customer, "
            + "f.selling_price, f.special_sale_price, f.deposit_date, f.selling_date, f.delivery_date, f.withdrawal_date_to_customer, "
            + "f.buyer,f.condition, f.unregistered_buyer_email, f.favourite_picture FROM project.furniture f WHERE f.favourite_picture = ?";
  }

  @Override
  public List<FurnitureDTO> selectAllFurniture() {
    try {
      PreparedStatement selectAllFurniture =
          dalServices.getPreparedStatement(querySelectAllFurniture);
      try (ResultSet rs = selectAllFurniture.executeQuery()) {
        List<FurnitureDTO> listFurniture = new ArrayList<>();
        FurnitureDTO furniture;
        do {
          furniture = createFurniture(rs);
          listFurniture.add(furniture);
        } while (furniture != null);
        listFurniture.remove(listFurniture.size() - 1);
        return listFurniture;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectAllFurnitures");
    }
  }

  @Override
  public List<FurnitureDTO> selectFurnitureUsers(int userId) {
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    try {
      PreparedStatement selectFurnitureUsers =
          dalServices.getPreparedStatement(querySelectFurnitureUser);
      selectFurnitureUsers.setInt(1, FurnitureCondition.en_vente.ordinal());
      try (ResultSet rs = selectFurnitureUsers.executeQuery()) {
        FurnitureDTO furniture;
        do {
          furniture = createFurniture(rs);
          list.add(furniture);
        } while (furniture != null);
        list.remove(list.size() - 1);
      }

      if (userId != -1) {
        PreparedStatement selectFurnituresInOptionUser =
            dalServices.getPreparedStatement(querySelectFurnituresInOptionUser);
        selectFurnituresInOptionUser.setInt(1, FurnitureCondition.en_option.ordinal());
        selectFurnituresInOptionUser.setInt(2, OptionStatus.en_cours.ordinal());
        selectFurnituresInOptionUser.setInt(3, userId);
        try (ResultSet rs = selectFurnituresInOptionUser.executeQuery()) {
          FurnitureDTO furniture;
          do {
            furniture = createFurniture(rs);
            list.add(furniture);
          } while (furniture != null);
          list.remove(list.size() - 1);
        }
      }
      return list;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectAllFurnituresUser");
    }
  }

  @Override
  public FurnitureDTO selectFurnitureById(int id) {
    try {
      PreparedStatement selectFurnitureById =
          dalServices.getPreparedStatement(querySelectFurnitureById);
      selectFurnitureById.setInt(1, id);
      try (ResultSet rs = selectFurnitureById.executeQuery()) {
        return createFurniture(rs);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnitureById");
    }
  }

  @Override
  public FurnitureDTO selectFurnitureByFavouritePicture(int idFavouritePicture) {
    try {
      PreparedStatement selectFurnitureByFavouritePicture =
          dalServices.getPreparedStatement(querySelectFurnitureByFavouritePicture);
      selectFurnitureByFavouritePicture.setInt(1, idFavouritePicture);
      try (ResultSet rs = selectFurnitureByFavouritePicture.executeQuery()) {
        return createFurniture(rs);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnitureByFavouritePicture");
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
      furniture.setCondition(FurnitureCondition.values()[rs.getInt("condition")]);
      furniture.setUnregisteredBuyerEmail(rs.getString("unregistered_buyer_email"));
    }
    return furniture;
  }



  @Override
  public List<FurnitureDTO> selectFurnitureByType(String type) {
    // TODO Auto-generated method stub
    return null;
    /*
     * List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>(); try { PreparedStatement selectFurnitureByType =
     * dalServices.getPreparedStatement(querySelectFurnitureByType); selectFurnitureByType.setString(1, type); ResultSet rs =
     * selectFurnitureByType.executeQuery(); while (rs.next()) { FurnitureDTO furniture = furnitureFactory.getFurniture(); listFurniture.add(furniture); }
     * return listFurniture; } catch (Exception e) { e.printStackTrace(); throw new FatalException("Data error : selectFurnitureByType"); }
     */
  }

  @Override
  public List<FurnitureDTO> selectFurnitureByPrice(double price) {
    // TODO Auto-generated method stub
    return null;
    /*
     * List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>(); try { PreparedStatement selectFurnitureByPrice =
     * dalServices.getPreparedStatement(querySelectFurnitureByPrice); selectFurnitureByPrice.setDouble(1, price); ResultSet rs =
     * selectFurnitureByPrice.executeQuery(); while (rs.next()) { FurnitureDTO furniture = furnitureFactory.getFurniture(); listFurniture.add(furniture);
     * } return listFurniture; } catch (Exception e) { e.printStackTrace(); throw new FatalException("Data error : selectFurnitureByPrice"); }
     */
  }

  @Override
  public List<FurnitureDTO> selectFurnitureByUser(String lastNameCustomer) {
    // TODO Auto-generated method stub
    return null;
    /*
     * List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>(); try { PreparedStatement selectFurnitureByUser =
     * dalServices.getPreparedStatement(querySelectFurnitureByUser); ResultSet rs = selectFurnitureByUser.executeQuery(); while (rs.next()) { FurnitureDTO
     * furniture = furnitureFactory.getFurniture(); listFurniture.add(furniture); } return listFurniture; } catch (Exception e) { e.printStackTrace();
     * throw new FatalException("Data error : selectFurnitureByUser"); }
     */
  }

  @Override
  public int insertFurniture(FurnitureDTO newFurniture) {
    // TODO Methode à tester
    int furnitureId = -1;
    try {
      PreparedStatement insertFurniture =
          this.dalServices.getPreparedStatement(queryInsertFurniture);
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
      insertFurniture.setString(13, newFurniture.getCondition().toString());
      insertFurniture.setString(14, newFurniture.getUnregisteredBuyerEmail());
      insertFurniture.setInt(15, newFurniture.getFavouritePicture().getId());
      insertFurniture.execute();
      try (ResultSet rs = insertFurniture.getGeneratedKeys()) {
        if (rs.next()) {
          furnitureId = rs.getInt(1);
        }
        return furnitureId;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : insertFurniture");
    }
  }

  @Override
  public boolean updateSellingDate(int id, Instant now) {
    try {
      PreparedStatement updateSellingDate =
          this.dalServices.getPreparedStatement(queryUpdateSellingDate);
      updateSellingDate.setTimestamp(1, Timestamp.from(now));
      updateSellingDate.setInt(2, id);
      return updateSellingDate.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateSellingDate");
    }
  }

  @Override
  public boolean updateSellingPrice(int id, Double price) {
    try {
      PreparedStatement updateSellingPrice =
          this.dalServices.getPreparedStatement(queryUpdateSellingPrice);
      updateSellingPrice.setDouble(1, price);
      updateSellingPrice.setInt(2, id);
      return updateSellingPrice.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateSellingPrice");
    }
  }

  @Override
  public boolean updateSpecialSalePrice(int id, Double price) {
    try {
      PreparedStatement updateSpecialSalePrice =
          this.dalServices.getPreparedStatement(queryUpdateSpecialSalePrice);
      updateSpecialSalePrice.setDouble(1, price);
      updateSpecialSalePrice.setInt(2, id);
      return updateSpecialSalePrice.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateSpecialSalePrice");
    }
  }

  @Override
  public boolean updateCondition(int id, int condition) {
    try {
      PreparedStatement updateCondition =
          this.dalServices.getPreparedStatement(queryUpdateCondition);
      updateCondition.setInt(1, condition);
      updateCondition.setInt(2, id);
      return updateCondition.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateCondition");
    }
  }

  @Override
  public boolean updateDepositDate(int id, Instant now) {
    try {
      PreparedStatement updateDepositDate =
          this.dalServices.getPreparedStatement(queryUpdateDepositDate);
      updateDepositDate.setTimestamp(1, Timestamp.from(now));
      updateDepositDate.setInt(2, id);
      return updateDepositDate.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateDepositDate");
    }
  }

  @Override
  public boolean updateType(int furnitureId, int typeId) {
    try {
      PreparedStatement updateType = this.dalServices.getPreparedStatement(queryUpdateType);
      updateType.setInt(1, typeId);
      updateType.setInt(2, furnitureId);
      return updateType.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateType");
    }
  }

  @Override
  public boolean updatePurchasePrice(int id, Double price) {
    try {
      PreparedStatement updatePurchasePrice =
          this.dalServices.getPreparedStatement(queryUpdatePurchasePrice);
      updatePurchasePrice.setDouble(1, price);
      updatePurchasePrice.setInt(2, id);
      return updatePurchasePrice.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : updatePurchasePrice");
    }
  }

  public boolean updateDescription(int id, String description) {
    try {
      PreparedStatement updateDescription =
          this.dalServices.getPreparedStatement(queryUpdateDescription);
      updateDescription.setString(1, description);
      updateDescription.setInt(2, id);
      return updateDescription.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateDescription");
    }
  }

  @Override
  public boolean updateWithdrawalDateToCustomer(int id, LocalDate now) {
    try {
      PreparedStatement updateWithdrawalDateToCustomer =
          this.dalServices.getPreparedStatement(queryUpdateWithdrawalDateToCustomer);
      if (now == null) {
        updateWithdrawalDateToCustomer.setTimestamp(1, null);
      } else {
        updateWithdrawalDateToCustomer.setTimestamp(1,
            Timestamp.valueOf(now.atTime(LocalTime.MIDNIGHT)));
      }
      updateWithdrawalDateToCustomer.setInt(2, id);
      return updateWithdrawalDateToCustomer.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateWithdrawalDateToCustomer");
    }
  }

  @Override
  public boolean updateWithdrawalDateFromCustomer(int id, LocalDate now) {
    try {
      PreparedStatement updateWithdrawalDateFromCustomer =
          this.dalServices.getPreparedStatement(queryUpdateWithdrawalDateFromCustomer);
      updateWithdrawalDateFromCustomer.setTimestamp(1,
          Timestamp.valueOf(now.atTime(LocalTime.MIDNIGHT)));
      updateWithdrawalDateFromCustomer.setInt(2, id);
      return updateWithdrawalDateFromCustomer.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateWithdrawalDateFromCustomer");
    }
  }

  @Override
  public boolean updateDeliveryDate(int id, LocalDate now) {
    try {
      PreparedStatement updateDeliveryDate =
          this.dalServices.getPreparedStatement(queryUpdateDeliveryDate);
      updateDeliveryDate.setTimestamp(1, Timestamp.valueOf(now.atTime(LocalTime.MIDNIGHT)));
      updateDeliveryDate.setInt(2, id);
      return updateDeliveryDate.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateDeliveryDate");
    }
  }

  @Override
  public boolean updateUnregisteredBuyerEmail(int id, String buyerEmail) {
    try {
      PreparedStatement updateUnregisteredBuyerEmail =
          this.dalServices.getPreparedStatement(queryUpdateUnregisteredBuyerEmail);
      updateUnregisteredBuyerEmail.setString(1, buyerEmail);
      updateUnregisteredBuyerEmail.setInt(2, id);
      return updateUnregisteredBuyerEmail.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateUnregisteredBuyerEmail");
    }
  }

  @Override
  public boolean updateBuyer(int id, int buyerId) {
    try {
      PreparedStatement updateUnregisteredBuyer =
          this.dalServices.getPreparedStatement(queryUpdateBuyer);
      if (buyerId == -1) {
        updateUnregisteredBuyer.setNull(1, java.sql.Types.INTEGER);
      } else {
        updateUnregisteredBuyer.setInt(1, buyerId);
      }
      updateUnregisteredBuyer.setInt(2, id);
      return updateUnregisteredBuyer.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateBuyer");
    }
  }



  @Override
  public List<FurnitureDTO> selectSalesFurniture() {
    try {
      PreparedStatement selectSalesFurniture =
          dalServices.getPreparedStatement(querySelectSalesFurniture);
      try (ResultSet rs = selectSalesFurniture.executeQuery()) {
        List<FurnitureDTO> listFurniture = new ArrayList<>();
        FurnitureDTO furniture;
        do {
          furniture = createFurniture(rs);
          listFurniture.add(furniture);
        } while (furniture != null);
        listFurniture.remove(listFurniture.size() - 1);
        return listFurniture;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectSalesFurnitures");
    }
  }

  @Override
  public List<FurnitureDTO> selectFurnituresFiltered(String type, double price, String username) {
    try {
      PreparedStatement selectFurnituresFiltered =
          dalServices.getPreparedStatement(querySelectFurnituresFiltered);
      selectFurnituresFiltered.setString(1, type + "%");
      selectFurnituresFiltered.setDouble(2, price);
      selectFurnituresFiltered.setDouble(3, price);
      selectFurnituresFiltered.setString(4, username + "%");
      try (ResultSet rs = selectFurnituresFiltered.executeQuery()) {
        List<FurnitureDTO> listFurniture = new ArrayList<>();
        FurnitureDTO furniture;
        do {
          furniture = createFurniture(rs);
          listFurniture.add(furniture);
        } while (furniture != null);
        listFurniture.remove(listFurniture.size() - 1);
        return listFurniture;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectFurnituresFiltered");
    }
  }

  @Override
  public List<FurnitureDTO> getFurnitureBuyBy(int id) {
    try {
      PreparedStatement selectFurnitureBuyBy =
          dalServices.getPreparedStatement(querySelectFurnitureBuyBy);
      selectFurnitureBuyBy.setInt(1, id);
      try (ResultSet rs = selectFurnitureBuyBy.executeQuery()) {
        List<FurnitureDTO> listFurniture = new ArrayList<>();
        FurnitureDTO furniture;
        do {
          furniture = createFurniture(rs);
          listFurniture.add(furniture);
        } while (furniture != null);
        listFurniture.remove(listFurniture.size() - 1);
        return listFurniture;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : getFurnitureBuyBy");
    }
  }

  @Override
  public List<FurnitureDTO> getFurnitureSellBy(int id) {
    try {
      PreparedStatement selectFurnitureSellBy =
          dalServices.getPreparedStatement(querySelectFurnitureSellBy);
      selectFurnitureSellBy.setInt(1, id);
      try (ResultSet rs = selectFurnitureSellBy.executeQuery()) {
        List<FurnitureDTO> listFurniture = new ArrayList<>();
        FurnitureDTO furniture;
        do {
          furniture = createFurniture(rs);
          listFurniture.add(furniture);
        } while (furniture != null);
        listFurniture.remove(listFurniture.size() - 1);
        return listFurniture;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : getFurnitureSellBy");
    }
  }



  public boolean updateFavouritePicture(int id, int pictureId) {
    try {
      PreparedStatement updateFavouritePicture =
          this.dalServices.getPreparedStatement(queryUpdateFavouritePicture);
      updateFavouritePicture.setInt(1, pictureId);
      updateFavouritePicture.setInt(2, id);
      return updateFavouritePicture.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateFavouritePicture");
    }

  }


}
