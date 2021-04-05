package be.vinci.pae.domain.user;

import java.util.List;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.user.DAOUser;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.ValueLink.UserType;
import jakarta.inject.Inject;

public class UserUCCImpl implements UserUCC {
  // @TODO dalServices.closeConnection() appel automatique ? Si possible
  // Try finally

  @Inject
  private DAOUser daoUser;

  @Inject
  private DalServices dalServices;

  @Override
  public UserDTO login(String username, String password) {
    try {
      User user = (User) this.daoUser.getUserByUsername(username);
      if (user == null || !user.checkPassword(password)) {
        throw new BusinessException("Pseudo ou mot de passe incorrect");
      }
      return user;
    } finally {
      dalServices.closeConnection();
    }
  }

  @Override
  public UserDTO register(UserDTO newUser) {
    this.dalServices.startTransaction();
    // si champs unique alors recherche dans DB si pas deja utilise
    User user = (User) this.daoUser.getUserByUsername(newUser.getUsername());
    if (user != null) {
      throw new BusinessException("Ce pseudo est deja utilise");
    }
    user = (User) this.daoUser.getUserByEmail(newUser.getEmail());
    if (user != null) {
      throw new BusinessException("Cet email est deja utilise");
    }

    user = (User) newUser;

    user.setPassword(user.hashPassword(user.getPassword()));

    int id = daoUser.addUser(newUser);
    if (id == -1) {
      this.dalServices.rollbackTransaction();
    } else {
      this.dalServices.commitTransaction();
    }
    user.setId(id);

    dalServices.closeConnection();
    return user;
  }

  @Override
  public boolean validateUser(int id, UserType type) {
    this.dalServices.startTransaction();
    User user = (User) daoUser.getUserById(id);
    if (user == null) {
      throw new BusinessException("L'utilisateur n'existe pas");
    }
    if (user.isValidRegistration()) {
      throw new BusinessException("L'utilisateur est déjà validé");
    }
    if (daoUser.validateUser(id, type.ordinal())) {
      this.dalServices.commitTransaction();
      dalServices.closeConnection();
      return true;
    } else {
      this.dalServices.rollbackTransaction();
      dalServices.closeConnection();
      return false;
    }
  }

  @Override
  public List<UserDTO> getUnvalidatedUsers() {
    List<UserDTO> list = daoUser.getUnvalidatedUsers();
    dalServices.closeConnection();
    return list;
  }

  @Override
  public UserDTO getUserById(int id) {
    UserDTO user = daoUser.getUserById(id);
    dalServices.closeConnection();
    return user;
  }
}
