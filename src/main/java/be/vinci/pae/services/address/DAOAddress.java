package be.vinci.pae.services.address;

import java.util.List;
import be.vinci.pae.domain.address.AddressDTO;

public interface DAOAddress {

  List<String> getAllCommunes();

  int addAddress(AddressDTO address);

  AddressDTO getAddressByUserId(int userId);
}
