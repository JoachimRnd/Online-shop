package be.vinci.pae.utils;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.domain.UserFactoryImpl;
import be.vinci.pae.services.DataServiceUserCollection;
import be.vinci.pae.services.DataServiceUserCollectionImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(UserFactoryImpl.class).to(UserFactory.class).in(Singleton.class);
    bind(DataServiceUserCollectionImpl.class).to(DataServiceUserCollection.class)
        .in(Singleton.class);
  }
}
