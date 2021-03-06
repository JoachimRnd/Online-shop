package be.vinci.pae.domain;

public interface UserUCC {

  UserDTO login(String login, String password);

  UserDTO register(String login, String password);

}
