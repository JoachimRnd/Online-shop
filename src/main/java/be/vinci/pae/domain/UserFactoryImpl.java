package be.vinci.pae.domain;

public class UserFactoryImpl implements UserFactory {

  @Override
  public UserDTO getUser() {
    return new UserImpl();
  }

}
