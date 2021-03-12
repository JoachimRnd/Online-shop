package be.vinci.pae.domain;

public interface UserUCC {

  UserDTO login(String username, String password);

  UserDTO register(UserDTO newUser);

}
