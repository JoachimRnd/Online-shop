package be.vinci.pae.domain.furniture;

import java.time.Instant;
import java.util.List;
import be.vinci.pae.domain.type.TypeDTO;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.services.type.DAOType;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import jakarta.inject.Inject;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  private DAOFurniture daoFurniture;

  @Inject
  private DAOType daoType;

  @Inject
  private DalServices dalServices;

  @Override
  public List<FurnitureDTO> getAllFurniture() {
    try {
      List<FurnitureDTO> listFurniture = this.daoFurniture.selectAllFurniture();
      return listFurniture;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public List<FurnitureDTO> getFurnitureUsers() {
    try {
      List<FurnitureDTO> listFurniture = this.daoFurniture.selectFurnitureUsers();
      return listFurniture;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public FurnitureDTO addFurniture(FurnitureDTO newFurniture) {
    // TODO A tester
    try {
      this.dalServices.startTransaction();
      int id = this.daoFurniture.insertFurniture(newFurniture);
      if (id == -1) {
        this.dalServices.rollbackTransaction();
      } else {
        this.dalServices.commitTransaction();
      }
      newFurniture.setId(id);
      return newFurniture;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public boolean modifyCondition(int id, FurnitureCondition condition, double price) {
    try {
      // TODO ajouter les etats manquants
      this.dalServices.startTransaction();

      boolean noError = true;

      switch (condition) {
        case en_vente:
          noError = noError && this.daoFurniture.updateSellingPrice(id, price)
              && this.daoFurniture.updateSellingDate(id, Instant.now());
          // fallthrough
        case en_magasin:
          noError = noError && this.daoFurniture.updateDepositDate(id, Instant.now());
          // fallthrough
        default:
          noError = noError && this.daoFurniture.updateCondition(id, condition.ordinal());
      }
      if (!noError) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify condition");
      }
      this.dalServices.commitTransaction();
      return true;

    } finally {
      this.dalServices.closeConnection();
    }


  }

  @Override
  public boolean modifyType(int furnitureId, int typeId) {
    TypeDTO type = this.daoType.selectTypeById(typeId);
    if (type == null) {
      throw new BusinessException("Le type n'existe pas");
    }
    try {
      this.dalServices.startTransaction();

      if (!this.daoFurniture.updateType(furnitureId, typeId)) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify Type");
      }
      this.dalServices.commitTransaction();
      return true;
    } finally {
      this.dalServices.closeConnection();
    }

  }


  @Override
  public boolean modifyPurchasePrice(int id, double price) {
    try {
      this.dalServices.startTransaction();

      if (!this.daoFurniture.updatePurchasePrice(id, price)) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify purchase price");
      }
      this.dalServices.commitTransaction();
      return true;
    } finally {
      this.dalServices.closeConnection();
    }
  }


  @Override
  public boolean modifyDescription(int id, String description) {
    try {
      this.dalServices.startTransaction();

      if (!this.daoFurniture.updateDescription(id, description)) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify description");
      }
      this.dalServices.commitTransaction();
      return true;
    } finally {
      this.dalServices.closeConnection();
    }
  }



  @Override
  public boolean modifyWithdrawalDate(int id, Instant time) {
    // TODO Auto-generated method stub
    return false;
  }


  @Override
  public boolean modifyBuyerEmail(int id, String email) {
    // TODO Auto-generated method stub
    return false;
  }



  @Override
  public FurnitureDTO getFurnitureById(int id) {
    try {
      FurnitureDTO furniture = this.daoFurniture.selectFurnitureById(id);
      return furniture;
    } finally {
      dalServices.closeConnection();
    }
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
    try {
      return null;
      /*
       * this.dalServices.startTransaction(); List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>(); listFurniture =
       * this.daoFurniture.selectFurnitureByPrice(sellingPrice); dalServices.closeConnection(); return listFurniture;
       */


    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public List<FurnitureDTO> getFurnitureByUserName(String userName) {
    // TODO Auto-generated method stub
    try {
      return null;
      /*
       * this.dalServices.startTransaction(); List<FurnitureDTO> listFurniture = new ArrayList<FurnitureDTO>(); User user = (User)
       * this.daoUser.getUserByUsername(userName); if (user == null) { this.dalServices.rollbackTransaction(); } else { String idUser =
       * String.valueOf(user.getId()); listFurniture = this.daoFurniture.selectFurnitureByUser(idUser); this.dalServices.commitTransaction(); }
       * dalServices.closeConnection(); return listFurniture;
       */

    } finally {
      this.dalServices.closeConnection();
    }

  }

  @Override
  public List<FurnitureDTO> getSalesFurnitureAdmin() {
    try {
      List<FurnitureDTO> listFurniture = this.daoFurniture.selectSalesFurniture();
      return listFurniture;
    } finally {
      this.dalServices.closeConnection();
    }
  }
}
