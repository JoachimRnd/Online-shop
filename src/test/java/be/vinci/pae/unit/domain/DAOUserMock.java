package be.vinci.pae.unit.domain;

import java.util.List;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.services.DAOUser;
import jakarta.inject.Inject;

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
}
