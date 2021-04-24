package be.vinci.pae.unit.domain;

import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.user.UserFactory;
import be.vinci.pae.services.user.DAOUser;
import jakarta.inject.Inject;
import java.util.List;

public class DAOUserMock implements DAOUser {

  @Inject
  UserFactory userFactory;

  @Override
  public UserDTO getUserByUsername(String username) {
    return null;
  }

  @Override
  public UserDTO getUserByEmail(String email) {
    return null;
  }

  @Override
  public UserDTO getUserById(int id) {
    return null;
  }

  @Override
  public int addUser(UserDTO user) {
    return 0;
  }

  @Override
  public boolean validateUser(int id, int type) {
    return false;
  }

  @Override
  public List<UserDTO> getUnvalidatedUsers() {
    return null;
  }

  @Override
  public List<String> getAllUsername() {
    return null;
  }

  @Override
  public List<UserDTO> getUsersFiltered(String username, String postcode, String commune) {
    return null;
  }
}
