package be.vinci.pae.domain.user;

import java.util.List;
import be.vinci.pae.utils.ValueLink.UserType;

public interface UserUCC {

  UserDTO login(String username, String password);

  UserDTO register(UserDTO newUser);

  boolean validateUser(int id, UserType type);

  List<UserDTO> getUnvalidatedUsers();

  UserDTO getUserById(int id);
}
