package be.vinci.pae.domain;

import be.vinci.pae.services.DAOUser;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.ValueLiaison;
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
      throw new BusinessException("L'utilisateur n'existe pas");
    }
    if (user.isValidRegistration()) {
      throw new BusinessException("L'utilisateur est déjà validé");
    }
    return daoUser.validateUser(id, ValueLiaison.StringToIntUserType(type));
  }

  @Override
  public List<UserDTO> getUnvalidatedUsers() {
    return daoUser.getUnvalidatedUsers();
  }
}
