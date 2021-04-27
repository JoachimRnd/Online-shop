package be.vinci.pae.services.option;

import java.util.List;
import be.vinci.pae.domain.option.OptionDTO;

public interface DAOOption {

  // @TODO MÃ©thodes non utilisÃ©e => Supprimer ?
  // yep je suppose pour toutes le mléthodes non utilisées...

  int addOption(OptionDTO picture);

  List<OptionDTO> selectOptionsOfFurniture(int idFurniture);

  List<OptionDTO> selectOptionsOfBuyer(int idBuyer);

  List<OptionDTO> selectOptionsOfBuyerFromFurniture(int idBuyer, int idFurniture);

  boolean finishOption(int id);

  boolean cancelOption(OptionDTO optionToCancel);

  OptionDTO getLastOptionOfFurniture(int idFurniture);

  OptionDTO selectOptionByFurnitureId(int idFurniture);
}
