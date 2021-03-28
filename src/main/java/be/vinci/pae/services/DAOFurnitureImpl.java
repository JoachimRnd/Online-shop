package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
  private String querySelectAllFurnitures;
  private String querySelectFurnitureById;
  private String querySelectFurnitureByType;
  private String querySelectFurnitureByPrice;
  private String querySelectFurnitureByUser;

  @Inject
  private FurnitureFactory furnitureFactory;
  @Inject
  private DalBackendServices dalServices;


  // TODO verifier les sql requests
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
}
