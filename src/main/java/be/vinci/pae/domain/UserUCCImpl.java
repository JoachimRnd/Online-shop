package be.vinci.pae.domain;

import be.vinci.pae.services.DAOUser;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private DAOUser daoUser;

  @Inject
  private UserFactory userFactory;

  @Override
  public UserDTO login(String login, String password) throws IllegalArgumentException {

    User user = (User) this.daoUser.getUser(login);
    if (user == null || !user.checkPassword(password)) {
      throw new IllegalArgumentException("Pseudo ou mot de passe incorrect");
    }
    return user;
  }

  @Override
  public UserDTO register(String login, String password) throws IllegalArgumentException {
    User user = (User) this.daoUser.getUser(login);
    if (user != null) {
      throw new IllegalArgumentException("Ce pseudo est déjà utilisé");
    }

    user = userFactory.getUser();

    user.setPseudo(login);
    user.setMotDePasse(password);

    daoUser.addUser(user);

    return user;
  }
}
