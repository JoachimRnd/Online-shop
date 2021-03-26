package be.vinci.pae.domain;

import be.vinci.pae.services.DAOUser;
import be.vinci.pae.utils.BusinessException;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {

  @Inject
  private DAOUser daoUser;

  @Override
  public UserDTO login(String username, String password) {

    User user = (User) this.daoUser.getUserByUsername(username);
    if (user == null || !user.checkPassword(password)) {
      throw new BusinessException("Pseudo ou mot de passe incorrect");
    }
    return user;
  }

  @Override
  public UserDTO register(UserDTO newUser) {
    User user = (User) this.daoUser.getUserByUsername(newUser.getUsername());
    if (user != null) {
      throw new BusinessException("Ce pseudo est deja  utilise");
    }
    user = (User) this.daoUser.getUserByEmail(newUser.getEmail());
    if (user != null) {
      throw new BusinessException("Cet email est deja utilise");
    }

    user = (User) newUser;

    user.setPassword(user.hashPassword(user.getPassword()));

    int id = daoUser.addUser(newUser);

    user.setId(id);

    return user;
  }
}
