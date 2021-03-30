package be.vinci.pae.utils;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import be.vinci.pae.domain.AddressFactory;
import be.vinci.pae.domain.AddressFactoryImpl;
import be.vinci.pae.domain.FurnitureFactory;
import be.vinci.pae.domain.FurnitureFactoryImpl;
import be.vinci.pae.domain.FurnitureUCC;
import be.vinci.pae.domain.FurnitureUCCImpl;
import be.vinci.pae.domain.PictureFactory;
import be.vinci.pae.domain.PictureFactoryImpl;
import be.vinci.pae.domain.PictureUCC;
import be.vinci.pae.domain.PictureUCCImpl;
import be.vinci.pae.domain.TypeFactory;
import be.vinci.pae.domain.TypeFactoryImpl;
import be.vinci.pae.domain.TypeUCC;
import be.vinci.pae.domain.TypeUCCImpl;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.domain.UserFactoryImpl;
import be.vinci.pae.domain.UserUCC;
import be.vinci.pae.domain.UserUCCImpl;
import be.vinci.pae.domain.VisitRequestFactory;
import be.vinci.pae.domain.VisitRequestFactoryImpl;
import be.vinci.pae.domain.VisitRequestUCC;
import be.vinci.pae.domain.VisitRequestUCCImpl;
import be.vinci.pae.services.DAOFurniture;
import be.vinci.pae.services.DAOFurnitureImpl;
import be.vinci.pae.services.DAOPicture;
import be.vinci.pae.services.DAOPictureImpl;
import be.vinci.pae.services.DAOType;
import be.vinci.pae.services.DAOTypeImpl;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.services.DAOUserImpl;
import be.vinci.pae.services.DAOVisitRequest;
import be.vinci.pae.services.DAOVisitRequestImpl;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.DalServicesImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    // USER
    bind(UserFactoryImpl.class).to(UserFactory.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(DAOUserImpl.class).to(DAOUser.class).in(Singleton.class);

    // PICTURE
    bind(PictureFactoryImpl.class).to(PictureFactory.class).in(Singleton.class);
    bind(PictureUCCImpl.class).to(PictureUCC.class).in(Singleton.class);
    bind(DAOPictureImpl.class).to(DAOPicture.class).in(Singleton.class);

    // ADDRESS
    bind(AddressFactoryImpl.class).to(AddressFactory.class).in(Singleton.class);

    // VISIT REQUEST
    bind(VisitRequestFactoryImpl.class).to(VisitRequestFactory.class).in(Singleton.class);
    bind(VisitRequestUCCImpl.class).to(VisitRequestUCC.class).in(Singleton.class);
    bind(DAOVisitRequestImpl.class).to(DAOVisitRequest.class).in(Singleton.class);

    // FURNITURE
    bind(FurnitureUCCImpl.class).to(FurnitureUCC.class).in(Singleton.class);
    bind(DAOFurnitureImpl.class).to(DAOFurniture.class).in(Singleton.class);
    bind(FurnitureFactoryImpl.class).to(FurnitureFactory.class).in(Singleton.class);

    // TYPE
    bind(TypeFactoryImpl.class).to(TypeFactory.class).in(Singleton.class);
    bind(TypeUCCImpl.class).to(TypeUCC.class).in(Singleton.class);
    bind(DAOTypeImpl.class).to(DAOType.class).in(Singleton.class);

    // DALSERVICES
    bind(DalServicesImpl.class).to(DalServices.class).in(Singleton.class);
    bind(DalServicesImpl.class).to(DalBackendServices.class).in(Singleton.class);
  }
}
