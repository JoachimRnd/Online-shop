package be.vinci.pae.domain.option;

import be.vinci.pae.domain.user.UserDTO;

public interface OptionUCC {

  OptionDTO addOption(OptionDTO option);

  void cancelOption(int id, UserDTO user);

  void cancelOptionByAdmin(int idFurniture);

  OptionDTO getLastOptionOfFurniture(int idFurniture);
}