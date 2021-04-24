package be.vinci.pae.domain.option;

import be.vinci.pae.domain.user.UserDTO;

public interface OptionUCC {

  void cancelOption(int id, UserDTO user);

  void cancelOptionByAdmin(int idFurniture);

  OptionDTO getLastOptionOfFurniture(int idFurniture);

  OptionDTO addOption(int idFurniture, int duration, UserDTO user);
}