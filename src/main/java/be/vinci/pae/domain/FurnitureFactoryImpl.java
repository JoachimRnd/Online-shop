package be.vinci.pae.domain;

public class FurnitureFactoryImpl implements FurnitureFactory {

  @Override
  public FurnitureDTO getFurniture() {
    return new FurnitureImpl();
  }

}
