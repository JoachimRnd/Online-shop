package be.vinci.pae.domain;

import java.util.List;

public interface UserUCC {

  UserDTO login(String username, String password);

  UserDTO register(UserDTO newUser);

  boolean validateUser(int id, String type);

  List<UserDTO> getUnvalidatedUsers();
}
