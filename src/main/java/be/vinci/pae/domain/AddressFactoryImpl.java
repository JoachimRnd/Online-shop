package be.vinci.pae.domain;

public class AddressFactoryImpl implements AddressFactory {

  @Override
  public Address getAddress() {
    return new AddressImpl();
  }

}
