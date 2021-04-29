package be.vinci.pae.unit.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import be.vinci.pae.domain.furniture.FurnitureDTO;
import be.vinci.pae.domain.furniture.FurnitureFactory;
import be.vinci.pae.domain.picture.PictureDTO;
import be.vinci.pae.domain.picture.PictureFactory;
import be.vinci.pae.domain.picture.PictureUCCImpl;
import be.vinci.pae.domain.type.TypeDTO;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.services.picture.DAOPicture;
import be.vinci.pae.utils.BusinessException;

public class PictureUCCTest {

  PictureFactory pictureFactory;
  FurnitureFactory furnitureFactory;

  @Mock
  DalServices dalServices;

  @Mock
  DAOPicture daoPicture;
  @Mock
  DAOFurniture daoFurniture;

  @InjectMocks
  PictureUCCImpl pictureUCC;


  @BeforeEach
  void initAll() {
    MockitoAnnotations.initMocks(this);
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
    this.furnitureFactory = locator.getService(FurnitureFactory.class);
    this.pictureFactory = locator.getService(PictureFactory.class);

  }

  @DisplayName("test if picture is null")
  @Test
  public void pictureUCCNotNullTest() {
    assertNotNull(this.pictureUCC);
  }

  @DisplayName("test addType good parameters")
  @Test
  public void addPictureTest() {
    PictureDTO picture = pictureFactory.getPicture();
    Mockito.when(daoPicture.addPicture(picture)).thenReturn(1);
    // assertEquals(1, pictureUCC.addPicture(picture));
  }

  @DisplayName("test getFurnitureTypes ")
  @Test
  public void getPictureTypesTest() {
    List<TypeDTO> list = new ArrayList<TypeDTO>();
    // assertNotNull(pictureUCC.getFurnitureTypes());
    // assertTrue(pictureUCC.getFurnitureTypes().isEmpty());
    // TypeDTO furniture = pictureFactory.getType();
    // list.add(furniture);
    // Mockito.when(daoPicture.selectFurnitureTypes()).thenReturn(list);
    // assertEquals(list, pictureUCC.getFurnitureTypes());
    // assertTrue(pictureUCC.getFurnitureTypes().contains(furniture));
    // assertEquals(1, pictureUCC.getFurnitureTypes().size());
  }

  @DisplayName("test modifyScrollingPicture")
  @Test
  public void modifyScrollingPictureTest() {
    Mockito.when(daoPicture.updateScrollingPicture(1)).thenReturn(true);
    assertTrue(pictureUCC.modifyScrollingPicture(1));
    Mockito.when(daoPicture.updateScrollingPicture(1)).thenReturn(false);
    assertThrows(BusinessException.class, () -> pictureUCC.modifyScrollingPicture(1));
  }

  @DisplayName("test deletePicture")
  @Test
  public void deletePictureTest() {
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.selectFurnitureByFavouritePicture(1)).thenReturn(furniture);
    Mockito.when(daoPicture.deletePicture(1)).thenReturn(true);
    assertTrue(pictureUCC.deletePicture(1));
    Mockito.when(daoPicture.deletePicture(1)).thenReturn(false);
    assertThrows(BusinessException.class, () -> pictureUCC.deletePicture(1));
  }

}
