package be.vinci.pae.domain;

import be.vinci.pae.services.DAOOption;
import jakarta.inject.Inject;
import java.util.List;

public class OptionUCCImpl implements OptionUCC {

  @Inject
  private DAOOption daoOption;


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
    //@TODO
    return null;
  }


}
