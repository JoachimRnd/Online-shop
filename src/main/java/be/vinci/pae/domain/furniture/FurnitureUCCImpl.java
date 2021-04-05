package be.vinci.pae.domain.furniture;

import java.time.Instant;
import java.util.List;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import jakarta.inject.Inject;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  private DAOFurniture daoFurniture;

  @Inject
  private DalServices dalServices;

  @Override
  public List<FurnitureDTO> getAllFurniture() {
    List<FurnitureDTO> listFurniture = this.daoFurniture.selectAllFurniture();
    this.dalServices.closeConnection();
    return listFurniture;
  }

  @Override
  public FurnitureDTO addFurniture(FurnitureDTO newFurniture) {
    // TODO A tester
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
  public boolean modifyCondition(int id, FurnitureCondition condition, double price) {
    // TODO ajouter les etats manquants
    this.dalServices.startTransaction();
    FurnitureDTO furniture = this.daoFurniture.selectFurnitureById(id);
    if (furniture == null) {
      throw new BusinessException("Le meuble n'existe pas");
    }
    boolean noError = true;

    switch (condition) {
      case en_vente:
        noError = noError && this.daoFurniture.updateSellingPrice(furniture.getId(), price)
            && this.daoFurniture.updateSellingDate(furniture.getId(), Instant.now());
        // fallthrough
      case en_magasin:
        noError = noError && this.daoFurniture.updateDepositDate(furniture.getId(), Instant.now());
        // fallthrough
      default:
        noError =
            noError && this.daoFurniture.updateCondition(furniture.getId(), condition.ordinal());
    }
    if (!noError) {
      this.dalServices.rollbackTransaction();
      throw new BusinessException("Error modify condition");
    }

    this.dalServices.commitTransaction();
    this.dalServices.closeConnection();
    return true;
  }

  @Override
  public FurnitureDTO getFurnitureById(int id) {
    FurnitureDTO furniture = this.daoFurniture.selectFurnitureById(id);
    dalServices.closeConnection();
    return furniture;
  }

  @Override
  public List<FurnitureDTO> getFurnitureByTypeName(String typeName) {
    // TODO Auto-generated method stub
    return null;
    /*
     * this.dalServices.startTransaction(); List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>(); Type type = (Type)
     * this.daoType.selectTypeByName(typeName); if (type == null) { this.dalServices.rollbackTransaction(); } else { String idType = type.toString();
     * listFurniture = this.daoFurniture.selectFurnitureByType(idType); this.dalServices.commitTransaction(); } dalServices.closeConnection(); return
     * listFurniture;
     */
  }

  @Override
  public List<FurnitureDTO> getFurnitureBySellingPrice(double sellingPrice) {
    // TODO Auto-generated method stub
    return null;
    /*
     * this.dalServices.startTransaction(); List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>(); listFurniture =
     * this.daoFurniture.selectFurnitureByPrice(sellingPrice); dalServices.closeConnection(); return listFurniture;
     */
  }

  @Override
  public List<FurnitureDTO> getFurnitureByUserName(String userName) {
    // TODO Auto-generated method stub
    return null;
    /*
     * this.dalServices.startTransaction(); List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>(); User user = (User)
     * this.daoUser.getUserByUsername(userName); if (user == null) { this.dalServices.rollbackTransaction(); } else { String idUser =
     * String.valueOf(user.getId()); listFurniture = this.daoFurniture.selectFurnitureByUser(idUser); this.dalServices.commitTransaction(); }
     * dalServices.closeConnection(); return listFurniture;
     */
  }
}
