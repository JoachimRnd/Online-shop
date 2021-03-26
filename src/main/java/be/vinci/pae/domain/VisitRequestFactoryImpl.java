package be.vinci.pae.domain;

public class VisitRequestFactoryImpl implements VisitRequestFactory {

  @Override
  public VisitRequest getVisitRequest() {
    return new VisitRequestImpl();
  }

}
