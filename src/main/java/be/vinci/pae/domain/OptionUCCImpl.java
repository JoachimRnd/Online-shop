package be.vinci.pae.domain;

import java.util.List;
import be.vinci.pae.services.DAOFurniture;
import be.vinci.pae.services.DAOOption;
import be.vinci.pae.services.DAOPicture;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.utils.BusinessException;
import jakarta.inject.Inject;

public class OptionUCCImpl implements OptionUCC {

  @Inject
  private DAOPicture daoPicture;
  @Inject
  private DAOFurniture daoFurniture;
  @Inject
  private DAOOption daoOption;
  @Inject
  private DAOUser daoUser;

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
    Option option = (Option) daoOption.selectOptionByID(newOption.getId());
    if (option == null) {
      throw new BusinessException("il n'y a pas d'option ayant cet ID");
    }
    option = (Option) newOption;
    option.setBuyerId(option.getBuyerId());
    option.setFurnitureId(option.getFurnitureId());
    option.setDuration(option.getDuration());
    option.setDate(option.getDate());
    option.setStatus(option.getStatus());

    int id = daoOption.addOption(newOption);
    option.setId(id); // TODO verifier si ok

    return option;
  }


}
