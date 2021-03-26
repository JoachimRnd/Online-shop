package be.vinci.pae.domain;

public class AddressFactoryImpl implements AddressFactory {

  @Override
  public AddressDTO getAddress() {
    return new AddressImpl();
  }

}
