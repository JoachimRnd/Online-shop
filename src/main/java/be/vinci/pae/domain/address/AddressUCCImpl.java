package be.vinci.pae.domain.address;

import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.address.DAOAddress;
import jakarta.inject.Inject;
import java.util.List;

public class AddressUCCImpl implements AddressUCC {

  @Inject
  DalServices dalServices;

  @Inject
  DAOAddress daoAddress;

  @Override
  public List<String> getAllCommunes() {
    try {
      return daoAddress.getAllCommunes();
    } finally {
      dalServices.closeConnection();
    }
  }
}
