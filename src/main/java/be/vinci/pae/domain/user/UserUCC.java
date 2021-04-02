package be.vinci.pae.domain.user;

import java.util.List;

public interface UserUCC {

  UserDTO login(String username, String password);

  UserDTO register(UserDTO newUser);

  boolean validateUser(int id, String type);

  List<UserDTO> getUnvalidatedUsers();

  UserDTO getUserById(int id);
}
