package be.vinci.pae.services.address;

import be.vinci.pae.domain.address.AddressDTO;
import java.util.List;

public interface DAOAddress {

  List<String> getAllCommunes();

  int selectAddressID(AddressDTO addressDTO);

  int addAddress(AddressDTO addressDTO);

  AddressDTO getAddressById(int id);
}
