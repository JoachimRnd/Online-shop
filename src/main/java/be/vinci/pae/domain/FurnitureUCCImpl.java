package be.vinci.pae.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.services.DAOFurniture;
import be.vinci.pae.services.DAOType;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.services.DalServices;
import jakarta.inject.Inject;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  private DAOFurniture daoFurniture;
  // TODO create classes for Type
  @Inject
  private DAOType daoType;

  @Inject
  private DAOUser daoUser;

  @Inject
  private DalServices dalServices;

  @Override
  public List<FurnitureDTO> getAllFurniture() {
    // TODO DAO
    this.dalServices.startTransaction();
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    listFurniture = this.daoFurniture.selectAllFurniture();
    this.dalServices.closeConnection();
    return listFurniture;
  }

  @Override
  public FurnitureDTO addFurniture(FurnitureDTO newFurniture) {
    // TODO DAO
    this.dalServices.startTransaction();
    int id = this.daoFurniture.insertFurniture(newFurniture);
    if (id == -1) {
      this.dalServices.rollbackTransaction();
    } else {
      this.dalServices.commitTransaction();
    }
    newFurniture.setId(id);
    this.dalServices.closeConnection();
    return newFurniture;
  }

  @Override
  public FurnitureDTO modifySellingDate(FurnitureDTO furniture, String status) {
    // TODO DAO
    // il me semble que condition est un int --> not sure
    this.dalServices.startTransaction();
    FurnitureDTO furnitureDto = this.daoFurniture.selectFurnitureById(furniture.getId());
    if (furnitureDto == null) {
      this.dalServices.rollbackTransaction();
    } else {
      this.daoFurniture.updateSellingDate(furnitureDto.getId(), LocalDate.now());
      this.daoFurniture.updateCondition(furnitureDto.getId(), status);
      this.dalServices.commitTransaction();
    }
    this.dalServices.closeConnection();
    return furniture;
  }

  @Override
  public FurnitureDTO modifyDepositDate(FurnitureDTO furniture, String status) {
    // TODO DAO
    // il me semble que condition est un int --> not sure
    this.dalServices.startTransaction();
    FurnitureDTO furnitureDto = this.daoFurniture.selectFurnitureById(furniture.getId());
    if (furnitureDto == null) {
      this.dalServices.rollbackTransaction();
    } else {
      this.daoFurniture.updateDepositDate(furnitureDto.getId(), LocalDate.now());
      this.daoFurniture.updateCondition(furnitureDto.getId(), status);
      this.dalServices.commitTransaction();
    }
    this.dalServices.closeConnection();
    return furniture;
  }

  @Override
  public FurnitureDTO modifyWorkshopDate(FurnitureDTO furniture, String status) {
    // TODO DAO
    // il me semble que condition est un int --> not sure
    this.dalServices.startTransaction();
    FurnitureDTO furnitureDto = this.daoFurniture.selectFurnitureById(furniture.getId());
    if (furnitureDto == null) {
      this.dalServices.rollbackTransaction();
    } else {
      this.daoFurniture.updateCondition(furnitureDto.getId(), status);
      this.dalServices.commitTransaction();
    }
    this.dalServices.closeConnection();
    return furniture;
  }

  @Override
  public FurnitureDTO getFurnitureById(int id) {
    FurnitureDTO furniture = this.daoFurniture.selectFurnitureById(id);
    dalServices.closeConnection();
    return furniture;
  }

  @Override
  public List<FurnitureDTO> getFurnitureByTypeName(String typeName) {
    // TODO
    this.dalServices.startTransaction();
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    // change daoUser by daoType --> create new classes ! TODO
    Type type = (Type) this.daoType.selectTypeByName(typeName);
    if (type == null) {
      this.dalServices.rollbackTransaction();
    } else {
      // TODO
      String idType = type.toString();
      listFurniture = this.daoFurniture.selectFurnitureByType(idType);
      this.dalServices.commitTransaction();
    }
    dalServices.closeConnection();
    return listFurniture;
  }

  @Override
  public List<FurnitureDTO> getFurnitureBySellingPrice(double sellingPrice) {
    // TODO
    this.dalServices.startTransaction();
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    listFurniture = this.daoFurniture.selectFurnitureByPrice(sellingPrice);
    dalServices.closeConnection();
    return listFurniture;
  }

  @Override
  public List<FurnitureDTO> getFurnitureByUserName(String userName) {
    // TODO
    this.dalServices.startTransaction();
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    User user = (User) this.daoUser.getUserByUsername(userName);
    if (user == null) {
      this.dalServices.rollbackTransaction();
    } else {
      // TODO
      String idUser = String.valueOf(user.getId());
      listFurniture = this.daoFurniture.selectFurnitureByUser(idUser);
      this.dalServices.commitTransaction();
    }
    dalServices.closeConnection();
    return listFurniture;
  }

}
