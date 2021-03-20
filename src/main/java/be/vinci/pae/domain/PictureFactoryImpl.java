package be.vinci.pae.domain;

public class PictureFactoryImpl implements PictureFactory {

  @Override
  public PictureDTO getPicture() {
    return new PictureImpl();
  }

}

