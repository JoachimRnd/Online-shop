package be.vinci.pae.domain;

import be.vinci.pae.services.DAOFurniture;
import be.vinci.pae.services.DAOOption;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.ValueLiaison;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OptionUCCImpl implements OptionUCC {

  @Inject
  private DAOOption daoOption;

  @Inject
  private DAOFurniture daoFurniture;

  @Inject
  private DAOUser daoUser;

  @Inject
  private DalServices dalServices;


  @Override
  public List<OptionDTO> getAllOptions() {
    for (OptionDTO option : daoOption.selectAllOptions()) {
      if (!verifyOptionStatus(option)) {
        dalServices.startTransaction();
        if (daoOption.finishOption(option.getId())) {
          dalServices.commitTransaction();
        } else {
          dalServices.rollbackTransaction();
        }
      }
    }
    List<OptionDTO> list = daoOption.selectAllOptions();
    dalServices.closeConnection();
    return list;
  }

  @Override
  public List<OptionDTO> getAllOptionsByFurniture(int idFurniture) {
    if (daoFurniture.selectFurnitureById(idFurniture) == null) {
      throw new BusinessException("Le meuble n'existe pas");
    }
    for (OptionDTO option : daoOption.selectOptionsOfFurniture(idFurniture)) {
      if (!verifyOptionStatus(option)) {
        dalServices.startTransaction();
        if (daoOption.finishOption(option.getId())) {
          dalServices.commitTransaction();
        } else {
          dalServices.rollbackTransaction();
        }
      }
    }
    List<OptionDTO> list = daoOption.selectOptionsOfFurniture(idFurniture);
    dalServices.closeConnection();
    return list;
  }

  @Override
  public List<OptionDTO> getAllOptionsByBuyer(int idBuyer) {
    if (daoUser.getUserById(idBuyer) == null) {
      throw new BusinessException("L'utilisateur' n'existe pas");
    }
    for (OptionDTO option : daoOption.selectOptionsOfBuyer(idBuyer)) {
      if (!verifyOptionStatus(option)) {
        dalServices.startTransaction();
        if (daoOption.finishOption(option.getId())) {
          dalServices.commitTransaction();
        } else {
          dalServices.rollbackTransaction();
        }
      }
    }
    List<OptionDTO> list = daoOption.selectOptionsOfBuyer(idBuyer);
    dalServices.closeConnection();
    return list;
  }

  @Override
  public OptionDTO addOption(OptionDTO newOption) {
    dalServices.startTransaction();
    if (newOption.getDuration() > 5 || newOption.getDuration() < 1) {
      throw new BusinessException("Durée de l'option incorrecte");
    }
    if (daoFurniture.selectFurnitureById(newOption.getFurniture().getId()) == null) {
      throw new BusinessException("Le meuble n'existe pas");
    }
    List<OptionDTO> listPreviousOption = daoOption
        .selectOptionsOfFurniture(newOption.getFurniture().getId());
    for (OptionDTO option : listPreviousOption) {
      if (TimeUnit.DAYS.convert(newOption.getDate().getTime() - option.getDate().getTime(),
          TimeUnit.MILLISECONDS) <= option.getDuration()) {
        throw new BusinessException("Il y a deja une option en cours pour ce meuble");
      }
    }
    if (daoOption.selectOptionsOfBuyerFromFurniture(newOption.getBuyer().getId(),
        newOption.getFurniture().getId()) != null) {
      throw new BusinessException("Vous avez deja mis une option sur ce meuble");
    }
    int idOption = daoOption.addOption(newOption);
    if (idOption == -1) {
      dalServices.rollbackTransaction();
      return null;
    } else {
      dalServices.commitTransaction();
    }
    newOption.setId(idOption);
    dalServices.closeConnection();
    return newOption;
  }

  @Override
  public void cancelOption(int idFurniture, UserDTO user) {
    List<OptionDTO> list = daoOption.selectOptionsOfFurniture(idFurniture);
    if (list.size() == 0) {
      throw new BusinessException("Il n'y a pas d'option sur ce meuble");
    }
    OptionDTO optionToCancel = null;
    for (OptionDTO option : list) {
      if (option.getStatus().equals(ValueLiaison.RUNNING_OPTION_STRING)) {
        optionToCancel = option;
        break;
      }
    }
    if (optionToCancel == null) {
      throw new BusinessException("Il n'y a pas d'option en cours sur ce meuble");
    }
    if (optionToCancel.getBuyer().getId() != user.getId()) {
      throw new BusinessException("Vous ne pouvez pas annuler l'option d'un autre utilisateur");
    }
    boolean canceled = daoOption.cancelOption(optionToCancel);
    if (canceled) {
      dalServices.commitTransaction();
    } else {
      dalServices.rollbackTransaction();
    }
    dalServices.closeConnection();
  }

  private boolean verifyOptionStatus(OptionDTO option) {
    if (!option.getStatus().equals(ValueLiaison.RUNNING_OPTION_STRING)) {
      return true;
    }
    Date now = new Date();
    if (TimeUnit.DAYS.convert(now.getTime() - option.getDate().getTime(),
        TimeUnit.MILLISECONDS) <= option.getDuration()) {
      return true;
    }
    return false;
  }


}
