package be.vinci.pae.domain;

import be.vinci.pae.services.DAOFurniture;
import be.vinci.pae.services.DAOOption;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OptionUCCImpl implements OptionUCC {

  @Inject
  private DAOOption daoOption;

  @Inject
  private DAOFurniture daoFurniture;

  @Inject
  private DAOUser daoUser;


  @Override
  public List<OptionDTO> getAllOptions() {
    List<OptionDTO> liste = daoOption.selectAllOptions();
    return liste;
  }

  @Override
  public List<OptionDTO> getAllOptionsByFurniture(int idFurniture) {
    if (daoFurniture.selectFurnitureById(idFurniture) == null) {
      throw new BusinessException("Le meuble n'existe pas");
    }
    return daoOption.selectOptionsOfFurniture(idFurniture);
  }

  @Override
  public List<OptionDTO> getAllOptionsByBuyer(int idBuyer) {
    if (daoUser.getUserById(idBuyer) == null) {
      throw new BusinessException("L'utilisateur' n'existe pas");
    }
    return daoOption.selectOptionsOfBuyer(idBuyer);
  }

  @Override
  public OptionDTO addOption(OptionDTO newOption) {
    if (newOption.getDuration() > 5) {
      throw new BusinessException("Durée de l'option trop grande");
    }
    if (daoFurniture.selectFurnitureById(newOption.getFurniture().getId()) == null) {
      throw new BusinessException("Le meuble n'existe pas");
    }
    List<OptionDTO> listPreviousOption = daoOption
        .selectOptionsOfFurniture(newOption.getFurniture().getId());
    for (OptionDTO option : listPreviousOption) {
      if (TimeUnit.DAYS.convert(newOption.getDate().getTime() - option.getDate().getTime(),
          TimeUnit.MILLISECONDS) <= option.getDuration()) {
        throw new BusinessException("Il y a deja une otpion en cours pour ce meuble");
      }
    }
    if (daoUser.getUserById(newOption.getBuyer().getId()) == null) {
      throw new BusinessException("L'utilisateur' n'existe pas");
    }
    if (daoOption.selectOptionsOfBuyerFromFurniture(newOption.getBuyer().getId(),
        newOption.getFurniture().getId()) != null) {
      throw new BusinessException("Vous avez deja mis une option sur ce meuble");
    }
    int idOption = daoOption.addOption(newOption);
    if (idOption == -1) {
      throw new FatalException("Erreur lors de l'ajout de l'option");
    }
    newOption.setId(idOption);
    return newOption;
  }


}
