package be.vinci.pae.services;

import be.vinci.pae.domain.UserDTO;

public interface DAOUser {

  UserDTO getUser(String login);
  
  UserDTO getUser(int id);

  void addUser(UserDTO user);
}
