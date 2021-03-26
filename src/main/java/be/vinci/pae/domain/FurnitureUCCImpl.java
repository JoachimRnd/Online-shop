package be.vinci.pae.domain;

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
    Furniture furniture = (Furniture) daoFurniture.selectFurnitureById(newFurniture.getId());
    if(furniture == null) {
      throw new BusinessException("il n'y a pas de meuble ayant cet ID");
    }
    furniture = (Furniture) newFurniture;
    furniture.setDescription(furniture.getDescription());
    //y'a plus le type dans furniture
    furniture.setVisiteRequest(furniture.getVisiteRequest());
    furniture.setPurchasePrice(furniture.getPurchasePrice());
    furniture.setWithdrawalDateFromCustomer(furniture.getWithdrawalDateFromCustomer());
    furniture.setSellingPrice(furniture.getSellingPrice());
    furniture.setSpecialSalePrice(furniture.getSpecialSalePrice());
    furniture.setDepositDate(furniture.getDepositDate());
    furniture.setSellingDate(furniture.getSellingDate());
    furniture.setDeliveryDate(furniture.getDeliveryDate());
    furniture.setWithdrawalDateToCustomer(furniture.getWithdrawalDateToCustomer());
    furniture.setBuyer(furniture.getBuyer());
    furniture.setCondition(furniture.getCondition());
    furniture.setUnregisteredBuyerEmail(furniture.getUnregisteredBuyerEmail());
    
    int id = daoFurniture.addFurniture(newFurniture);
    if (id == -1) {
      this.dalServices.rollbackTransaction();
    }
    this.dalServices.commitTransaction();
    furniture.setId(furniture.getId());
    this.dalServices.closeConnection();
    return furniture;
  }

  @Override
  public List<FurnitureDTO> getFurnitureByTypeName(String typeName) {
    // TODO 
    
    return null;
  }

  @Override
  public List<FurnitureDTO> getFurnitureBySellingPrice(double sellingPrice) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<FurnitureDTO> getFurnitureByUserName(String typeName) {
    // TODO 
    User user = (User) this.daoUser.getUserByUsername(typeName);
    if (user == null) {
      throw new BusinessException("le client recherce n'existe pas");
    }
    int idUser = user.getId();
    List<FurnitureDTO> listFurniture = this.daoFurniture.selectFurnitureByBuyerId(idUser);
    if (listFurniture.isEmpty() || listFurniture == null) {
      throw new BusinessException("il n'y a pas de meuble associe au client recherche");
    }
    return listFurniture;
  }

}
