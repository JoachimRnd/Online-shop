package be.vinci.pae.domain.furniture;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import be.vinci.pae.domain.type.TypeDTO;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.services.type.DAOType;
import be.vinci.pae.services.user.DAOUser;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;
import jakarta.inject.Inject;

public class FurnitureUCCImpl implements FurnitureUCC {

  @Inject
  private DAOUser daoUser;

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
      checkFurnitures(listFurniture);
      return listFurniture;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public List<FurnitureDTO> getFurnitureUsers() {
    try {
      List<FurnitureDTO> listFurniture = this.daoFurniture.selectFurnitureUsers();
      checkFurnitures(listFurniture);
      return listFurniture;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  private void checkFurniture(FurnitureDTO furnitureDTO) {
    if (furnitureDTO.getWithdrawalDateToCustomer() != null) {
      if (furnitureDTO.getCondition() == FurnitureCondition.vendu) {
        if (furnitureDTO.getWithdrawalDateToCustomer().getTime() < (new Date().getTime())) {
          modifyCondition(furnitureDTO.getId(), FurnitureCondition.reserve);
          furnitureDTO.setCondition(FurnitureCondition.reserve);
        }
      }
      if (furnitureDTO.getCondition() == FurnitureCondition.reserve) {
        Date dateOneYearAndOneDayLater = furnitureDTO.getWithdrawalDateToCustomer();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOneYearAndOneDayLater);
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DATE, 1);
        dateOneYearAndOneDayLater = cal.getTime();

        if (new Date().getTime() > dateOneYearAndOneDayLater.getTime()) {
          modifyCondition(furnitureDTO.getId(), FurnitureCondition.en_vente);
          modifyWithdrawalDateToCustomer(furnitureDTO.getId(), null);
          modifyBuyerEmail(furnitureDTO.getId(), null);
          furnitureDTO.setUnregisteredBuyerEmail(null);
          furnitureDTO.setWithdrawalDateToCustomer(null);
          furnitureDTO.setCondition(FurnitureCondition.en_vente);
        }
      }

    }
  }



  private void checkFurnitures(List<FurnitureDTO> listFurniture) {
    for (FurnitureDTO furnitureDTO : listFurniture) {
      checkFurniture(furnitureDTO);
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
  public boolean modifyCondition(int id, FurnitureCondition condition) {
    try {
      // TODO ajouter les etats manquants
      this.dalServices.startTransaction();
      boolean noError = true;

      switch (condition) {
        case en_vente:
          noError = noError && this.daoFurniture.updateSellingDate(id, Instant.now());
        case en_magasin:
          noError = noError && this.daoFurniture.updateDepositDate(id, Instant.now());
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
      throw new BusinessException("Type doesn't exist");
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
  public boolean modifySellingPrice(int id, double price) {
    try {
      this.dalServices.startTransaction();

      if (!this.daoFurniture.updateSellingPrice(id, price)) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify selling price");
      }
      this.dalServices.commitTransaction();
      return true;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public boolean modifySpecialSalePrice(int id, double price) {
    try {
      this.dalServices.startTransaction();

      if (!this.daoFurniture.updateSpecialSalePrice(id, price)) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify special sale price");
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
  public boolean modifyWithdrawalDateToCustomer(int id, LocalDate time) {
    try {
      this.dalServices.startTransaction();

      if (!this.daoFurniture.updateWithdrawalDateToCustomer(id, time)) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify WithdrawalDateToCustomer");
      }
      this.dalServices.commitTransaction();
      return true;
    } finally {
      this.dalServices.closeConnection();
    }

  }

  @Override
  public boolean modifyWithdrawalDateFromCustomer(int id, LocalDate time) {

    try {
      this.dalServices.startTransaction();

      if (!this.daoFurniture.updateWithdrawalDateFromCustomer(id, time)) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify WithdrawalDateFromCustomer");
      }
      this.dalServices.commitTransaction();
      return true;
    } finally {
      this.dalServices.closeConnection();
    }

  }

  @Override
  public boolean modifyDeliveryDate(int id, LocalDate time) {

    try {
      this.dalServices.startTransaction();

      if (!this.daoFurniture.updateDeliveryDate(id, time)) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify delivery date");
      }
      this.dalServices.commitTransaction();
      return true;
    } finally {
      this.dalServices.closeConnection();
    }

  }


  @Override
  public boolean modifyBuyerEmail(int id, String email) {
    UserDTO userDTO;
    if (email == null) {
      FurnitureDTO furnitureDTO;
      try {
        furnitureDTO = this.daoFurniture.selectFurnitureById(id);
      } finally {
        this.dalServices.closeConnection();
      }
      userDTO = furnitureDTO.getBuyer();
    } else {
      try {
        userDTO = this.daoUser.getUserByEmail(email);
      } finally {
        this.dalServices.closeConnection();
      }
    }

    try {
      // User exist
      if (userDTO != null) {
        this.dalServices.startTransaction();
        int idUser = userDTO.getId();
        if (email == null) {
          idUser = -1;
        }
        if (!this.daoFurniture.updateBuyer(id, idUser)) {
          this.dalServices.rollbackTransaction();
          throw new BusinessException("Error modify buyer");
        }
        this.dalServices.commitTransaction();
        return true;
      } else { // User doesn't exist
        this.dalServices.startTransaction();

        if (!this.daoFurniture.updateUnregisteredBuyerEmail(id, email)) {
          this.dalServices.rollbackTransaction();
          throw new BusinessException("Error modify buyer email");
        }
        this.dalServices.commitTransaction();
        return true;
      }
    } finally {
      this.dalServices.closeConnection();
    }
  }



  @Override
  public FurnitureDTO getFurnitureById(int id) {
    try {
      FurnitureDTO furniture = this.daoFurniture.selectFurnitureById(id);
      checkFurniture(furniture);
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
