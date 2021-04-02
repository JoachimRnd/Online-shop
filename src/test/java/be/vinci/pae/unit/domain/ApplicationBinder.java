package be.vinci.pae.unit.domain;

import be.vinci.pae.domain.user.UserFactory;
import be.vinci.pae.domain.user.UserFactoryImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {


  @Override
  protected void configure() {
    bind(UserFactoryImpl.class).to(UserFactory.class).in(Singleton.class);
    // bind(DalServicesMock.class).to(DalServices.class).in(Singleton.class);
    // bind(DAOUserMock.class).to(DAOUser.class).in(Singleton.class);

  }
}
