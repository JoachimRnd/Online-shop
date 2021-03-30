package be.vinci.pae.services;

import be.vinci.pae.domain.OptionDTO;
import java.util.List;

public interface DAOOption {

  List<OptionDTO> selectAllOptions();

  int addOption(OptionDTO picture);

  OptionDTO selectOptionByID(int id);

  List<OptionDTO> selectOptionsOfFurniture(int idFurniture);

  List<OptionDTO> selectOptionsOfBuyer(int idBuyer);

  OptionDTO selectOptionsOfBuyerFromFurniture(int idBuyer, int idFurniture);

}