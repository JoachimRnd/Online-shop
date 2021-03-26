package be.vinci.pae.domain;

public class OptionFactoryImpl implements OptionFactory {

  @Override
  public OptionDTO getOption() {
    return new OptionImpl();
  }

}