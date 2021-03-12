package be.vinci.pae.services;

import be.vinci.pae.domain.UserDTO;

public interface DAOUser {

  UserDTO getUserByUsername(String username);

  UserDTO getUserByEmail(String email);

  UserDTO getUserById(int id);

  int addUser(UserDTO user);
}
