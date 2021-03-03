package be.vinci.pae.domain;

public class AdresseFactoryImpl implements AdresseFactory {

  @Override
  public Adresse getAdresse() {
    return new AdresseImpl();
  }

}
