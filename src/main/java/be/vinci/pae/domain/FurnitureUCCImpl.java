package be.vinci.pae.domain;

import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.services.DAOFurniture;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.utils.BusinessException;
import jakarta.inject.Inject;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  private DAOFurniture daoFurniture;

  @Inject
  private DAOUser daoUser;

  @Inject
  private DalServices dalServices;

  @Override
  public List<FurnitureDTO> getAllFurniture() {
    // TODO Auto-generated method stub
    return null;
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

    return null;
  }

  @Override
  public List<FurnitureDTO> getFurnitureBySellingPrice(double sellingPrice) {
    // TODO 
    this.dalServices.startTransaction();
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    listFurniture = this.daoFurniture.selectFurnitureByPrice(sellingPrice);
    if (listFurniture == null){
      this.dalServices.rollbackTransaction();
    } else {
      this.dalServices.commitTransaction();
    }
    dalServices.closeConnection();
    return listFurniture;
  }

  @Override
  public List<FurnitureDTO> getFurnitureByUserName(String typeName) {
    // TODO
    this.dalServices.startTransaction();
    List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>();
    User user = (User) this.daoUser.getUserByUsername(typeName);
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
