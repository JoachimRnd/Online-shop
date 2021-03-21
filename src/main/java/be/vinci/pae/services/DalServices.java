package be.vinci.pae.services;

public interface DalServices {

  void startTransaction();

  void startTransactionSetAutoCommitToFalse();

  void commitTransaction();

  void rollbackTransaction();

}
