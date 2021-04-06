package be.vinci.pae.domain.option;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.services.option.DAOOption;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.ValueLink.OptionStatus;
import jakarta.inject.Inject;

public class OptionUCCImpl implements OptionUCC {

  @Inject
  private DAOOption daoOption;

  @Inject
  private DAOFurniture daoFurniture;

  @Inject
  private DalServices dalServices;

  @Override
  public OptionDTO addOption(OptionDTO newOption) {

    try {

      // TODO changer l'état du meuble
      // TODO test à faire pour voir si on peut mettre l'option (etat user, etat du meuble)
      dalServices.startTransaction();
      if (newOption.getDuration() > 5 || newOption.getDuration() < 1) {
        throw new BusinessException("Durée de l'option incorrecte");
      }
      if (daoFurniture.selectFurnitureById(newOption.getFurniture().getId()) == null) {
        throw new BusinessException("Le meuble n'existe pas");
      }
      List<OptionDTO> listPreviousOption =
          daoOption.selectOptionsOfFurniture(newOption.getFurniture().getId());
      for (OptionDTO option : listPreviousOption) {
        if (option.getStatus().equals(OptionStatus.en_cours)
            && TimeUnit.DAYS.convert(newOption.getDate().getTime() - option.getDate().getTime(),
                TimeUnit.MILLISECONDS) <= option.getDuration()) {
          throw new BusinessException("Il y a deja une option en cours pour ce meuble");
        }
      }
      if (daoOption.selectOptionsOfBuyerFromFurniture(newOption.getBuyer().getId(),
          newOption.getFurniture().getId()) != null
          && !daoOption.selectOptionsOfBuyerFromFurniture(newOption.getBuyer().getId(),
              newOption.getFurniture().getId()).getStatus().equals(OptionStatus.annulee)) {
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
      return newOption;
    } finally {
      dalServices.closeConnection();
    }


  }

  @Override
  public void cancelOption(int idFurniture, UserDTO user) {

    try {
      dalServices.startTransaction();
      List<OptionDTO> list = daoOption.selectOptionsOfFurniture(idFurniture);
      if (list.size() == 0) {
        throw new BusinessException("Il n'y a pas d'option sur ce meuble");
      }
      OptionDTO optionToCancel = null;
      for (OptionDTO option : list) {
        if (option.getStatus().equals(OptionStatus.en_cours)) {
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
    } finally {
      dalServices.closeConnection();
    }

  }

  @Override
  public void cancelOptionByAdmin(int idFurniture) {
    try {
      dalServices.startTransaction();
      List<OptionDTO> list = daoOption.selectOptionsOfFurniture(idFurniture);
      if (list.size() == 0) {
        throw new BusinessException("Il n'y a pas d'option sur ce meuble");
      }
      OptionDTO optionToCancel = null;
      for (OptionDTO option : list) {
        if (option.getStatus().equals(OptionStatus.en_cours)) {
          optionToCancel = option;
          break;
        }
      }
      if (optionToCancel == null) {
        throw new BusinessException("Il n'y a pas d'option en cours sur ce meuble");
      }
      boolean canceled = daoOption.cancelOption(optionToCancel);
      if (canceled) {
        dalServices.commitTransaction();
      } else {
        dalServices.rollbackTransaction();
      }
    } finally {
      dalServices.closeConnection();
    }
  }

  @Override
  public OptionDTO getLastOptionOfFurniture(int idFurniture) {
    try {
      if (daoFurniture.selectFurnitureById(idFurniture) == null) {
        throw new BusinessException("Ce meuble n'existe pas");
      }
      OptionDTO option = daoOption.getLastOptionOfFurniture(idFurniture);
      if (!verifyOptionStatus(option)) {
        dalServices.startTransaction();
        if (daoOption.finishOption(option.getId())) {
          dalServices.commitTransaction();
        } else {
          dalServices.rollbackTransaction();
        }
        option = daoOption.getLastOptionOfFurniture(idFurniture);
      }
      System.out.println(option);
      return option;
    } finally {
      dalServices.closeConnection();
    }
  }


  // TODO voir si ce n'est pas automatisable
  private boolean verifyOptionStatus(OptionDTO option) {
    if (option == null) {
      return true;
    }
    if (!option.getStatus().equals(OptionStatus.en_cours)) {
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
