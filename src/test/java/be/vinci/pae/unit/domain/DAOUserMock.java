package be.vinci.pae.unit.domain;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.services.DAOUser;
import jakarta.inject.Inject;

public class DAOUserMock implements DAOUser {

  @Inject
  UserFactory userFactory;

  @Override
  public UserDTO getUser(String login) {
    if (login.equals("login")) {
      User user = (User) userFactory.getUser();
      user.setPseudo("login");
      user.setMotDePasse(user.hashPassword("password"));
      return user;
    }
    return null;
  }

  @Override
  public UserDTO getUser(int id) {
    return null;
  }

  @Override
  public void addUser(UserDTO user) {
  }
}
