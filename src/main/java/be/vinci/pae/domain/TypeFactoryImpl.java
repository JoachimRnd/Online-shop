package be.vinci.pae.domain;

public class TypeFactoryImpl implements TypeFactory {

  @Override
  public TypeDTO getType() {
    return new TypeImpl();
  }
}
