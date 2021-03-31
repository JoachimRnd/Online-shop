package be.vinci.pae.unit.domain;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.domain.UserFactoryImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationBinder extends AbstractBinder {


  @Override
  protected void configure() {
    bind(UserFactoryImpl.class).to(UserFactory.class).in(Singleton.class);
    // bind(DalServicesMock.class).to(DalServices.class).in(Singleton.class);
    // bind(DAOUserMock.class).to(DAOUser.class).in(Singleton.class);

  }
}
