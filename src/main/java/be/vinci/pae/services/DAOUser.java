package be.vinci.pae.services;

import be.vinci.pae.domain.User;

public interface DAOUser {

  User getUser(String login);


  User getUser(int id);

  void addUser(User user);
}
