package be.vinci.pae.unit.domain;

import be.vinci.pae.services.DalServices;

public class DalServicesMock implements DalServices {

  @Override
  public void startTransaction() {

  }

  @Override
  public void commitTransaction() {

  }

  @Override
  public void rollbackTransaction() {

  }

  @Override
  public void closeConnection() {

  }

  @Override
  public void rollbackMultipleTransaction() {
    
  }

  @Override
  public void commitMultipleTransactions() {

  }

  @Override
  public void startMultipleTransactions() {

  }
}
