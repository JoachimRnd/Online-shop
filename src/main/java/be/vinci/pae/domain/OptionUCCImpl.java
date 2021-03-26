package be.vinci.pae.domain;

import java.util.List;
import be.vinci.pae.services.DAOOption;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.utils.BusinessException;
import jakarta.inject.Inject;

public class OptionUCCImpl implements OptionUCC {

  @Inject
  private DAOOption daoOption;
  
  @Inject
  private DalServices dalServices;


  @Override
  public List<OptionDTO> getAllOptions() {
    List<OptionDTO> liste = daoOption.selectAllOptions();
    return liste;
  }

  @Override
  public List<OptionDTO> getAllOptionsByFurniture(int idFurniture) {
    // TODO
    return null;
  }

  @Override
  public List<OptionDTO> getAllOptionsByBuyer(int idBuyer) {
    // TODO
    return null;
  }

  @Override
  public OptionDTO addOption(OptionDTO newOption) {
    this.dalServices.startTransaction();
    int id = daoOption.addOption(newOption);
    if (id == -1) {
      this.dalServices.rollbackTransaction();
    }
    else {
      this.dalServices.commitTransaction();
    }
    newOption.setId(id); 
    this.dalServices.closeConnection();
    return newOption;
  }


}
