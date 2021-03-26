package be.vinci.pae.services;

import be.vinci.pae.domain.UserDTO;
import java.util.List;

public interface DAOUser {

  UserDTO getUserByUsername(String username);

  UserDTO getUserByEmail(String email);

  UserDTO getUserById(int id);

  int addUser(UserDTO user);

  boolean validateUser(int id, int type);

  List<UserDTO> getUnvalidatedUsers();
}
