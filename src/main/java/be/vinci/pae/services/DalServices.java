package be.vinci.pae.services;

public interface DalServices {

  void startTransaction();

  void commitTransaction();

  void rollbackTransaction();

  void closeConnection();

}
