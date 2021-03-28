package be.vinci.pae.domain;

import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.services.DAOFurniture;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.services.DalServices;
import jakarta.inject.Inject;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  private DAOFurniture daoFurniture;
  // TODO create classes for Type

  @Inject
  private DAOUser daoUser;

  @Inject
  private DalServices dalServices;

  @Override
  public List<FurnitureDTO> getAllFurniture() {
    // TODO this.dalServices.startTransaction();
    this.dalServices.startTransaction();
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    listFurniture = this.daoFurniture.selectAllFurniture();
    if (listFurniture == null) {
      this.dalServices.rollbackTransaction();
    } else {
      this.dalServices.commitTransaction();
    }
    dalServices.closeConnection();
    return listFurniture;
  }

  @Override
  public FurnitureDTO addFurniture(FurnitureDTO newFurniture) {
    this.dalServices.startTransaction();
    int id = daoFurniture.addFurniture(newFurniture);
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
  public List<FurnitureDTO> getFurnitureByTypeName(String typeName) {
    // TODO
    this.dalServices.startTransaction();
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    // change daoUser by daoType --> create new classes ! TODO
    // TODO
    // TODO
    // TODO
    User type = (User) this.daoUser.selectTypeByName(typeName);
    if (type == null) {
      this.dalServices.rollbackTransaction();
    } else {
      int idType = type.getId();
      listFurniture = this.daoFurniture.selectFurnitureByTypeName(idType);
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
      int idUser = user.getId();
      listFurniture = this.daoFurniture.selectFurnitureByBuyerId(idUser);
      this.dalServices.commitTransaction();
    }
    dalServices.closeConnection();
    return listFurniture;
  }

}
