package be.vinci.pae.unit.domain;

import be.vinci.pae.services.DalServices;

public class DalServicesMock implements DalServices {

  @Override
  public void startTransaction() {
    
  }

  @Override
  public void startTransactionSetAutoCommitToFalse() {

  }

  @Override
  public void commitTransaction() {

  }

  @Override
  public void rollbackTransaction() {

  }
}
