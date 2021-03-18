package be.vinci.pae.domain;

import be.vinci.pae.services.DAOUser;
import be.vinci.pae.utils.BusinessException;
import jakarta.inject.Inject;
import java.util.List;

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
      throw new BusinessException("Ce pseudo est déjà utilisé");
    }
    user = (User) this.daoUser.getUserByEmail(newUser.getEmail());
    if (user != null) {
      throw new BusinessException("Cet Email est déjà utilisé");
    }

    user = (User) newUser;

    user.setPassword(user.hashPassword(user.getPassword()));

    int id = daoUser.addUser(newUser);

    user.setId(id);

    return user;
  }

  @Override
  public boolean validateUser(int id, String type) {
    User user = (User) daoUser.getUserById(id);
    if (user == null) {
      //erreur user inexistant
    }
    if (user.isValidRegistration()) {
      //erreur user déjà validé
    }
    //@TODO Trouver un moyen de lier une string a un int pour la DB
    int typeInt = -1;
    return daoUser.validateUser(id, typeInt);
  }

  @Override
  public List<UserDTO> getUnvalidatedUsers() {
    return daoUser.getUnvalidatedUsers();
  }
}
