package be.vinci.pae.utils;

import be.vinci.pae.domain.AddressFactory;
import be.vinci.pae.domain.AddressFactoryImpl;
import be.vinci.pae.domain.PictureFactory;
import be.vinci.pae.domain.PictureFactoryImpl;
import be.vinci.pae.domain.PictureUCC;
import be.vinci.pae.domain.PictureUCCImpl;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.domain.UserFactoryImpl;
import be.vinci.pae.domain.UserUCC;
import be.vinci.pae.domain.UserUCCImpl;
import be.vinci.pae.services.DAOPicture;
import be.vinci.pae.services.DAOPictureImpl;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.services.DAOUserImpl;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.DalServicesImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(UserFactoryImpl.class).to(UserFactory.class).in(Singleton.class);
    bind(AddressFactoryImpl.class).to(AddressFactory.class).in(Singleton.class);
    bind(PictureFactoryImpl.class).to(PictureFactory.class).in(Singleton.class);
    bind(DAOUserImpl.class).to(DAOUser.class).in(Singleton.class);
    bind(DAOPictureImpl.class).to(DAOPicture.class).in(Singleton.class);
    bind(DalServicesImpl.class).to(DalServices.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(PictureUCCImpl.class).to(PictureUCC.class).in(Singleton.class);
  }
}
