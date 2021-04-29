package be.vinci.pae.unit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
import be.vinci.pae.domain.option.OptionDTO;
import be.vinci.pae.domain.option.OptionFactory;
import be.vinci.pae.domain.option.OptionUCCImpl;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.user.UserFactory;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.services.option.DAOOption;
import be.vinci.pae.services.user.DAOUser;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.ValueLink;


public class OptionUCCTest {

  OptionFactory optionFactory;

  UserFactory userFactory;

  FurnitureFactory furnitureFactory;

  @Mock
  DalServices dalServices;
  @Mock
  DAOOption daoOption;
  @Mock
  DAOFurniture daoFurniture;
  @Mock
  DAOUser daoUser;

  @InjectMocks
  OptionUCCImpl optionUCC;


  @BeforeEach
  void initAll() {
    MockitoAnnotations.initMocks(this);
    // l'init foire car durations dans le .properties...

    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
    this.optionFactory = locator.getService(OptionFactory.class);
    this.furnitureFactory = locator.getService(FurnitureFactory.class);
    this.userFactory = locator.getService(UserFactory.class);
    Config.load("dev.properties");

  }

  @DisplayName("test if option is null")
  @Test
  public void optionUCCNotNullTest() {
    assertNotNull(this.optionUCC);
  }

  @DisplayName("test addOption good parameters")
  @Test
  public void addOptionAllGoodTest() {
    UserDTO buyer = userFactory.getUser();
    Mockito.when(daoUser.getUserById(1)).thenReturn(buyer);
    buyer.setValidRegistration(true);
    buyer.setId(1);
    buyer.setUserType(ValueLink.UserType.client);
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    furniture.setCondition(ValueLink.FurnitureCondition.en_vente);
    // Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    // furniture.setCondition(ValueLink.FurnitureCondition.en_vente);
    OptionDTO optionToAdd = optionFactory.getOption();
    // Mockito.when(daoOption.addOption(optionToAdd)).thenReturn(1);
    // buyer.setValidRegistration(true);
    // buyer.setId(1);
    // buyer.setUserType(ValueLink.UserType.client);
    Mockito.when(daoUser.getUserById(1)).thenReturn(buyer);
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    Mockito.when(daoOption.addOption(optionToAdd)).thenReturn(1);

    assertTrue(optionUCC.addOption(1, 2, buyer));
  }

  @DisplayName("test addOption bad furniture only")
  @Test
  public void addOptionBadFurnitureTest() {
    UserDTO buyer = userFactory.getUser();
    Mockito.when(daoUser.getUserById(1)).thenReturn(buyer);
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(null);
    assertThrows(BusinessException.class, () -> optionUCC.addOption(1, 2, buyer));

    FurnitureDTO furniture = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    furniture.setCondition(ValueLink.FurnitureCondition.emporte_par_patron);
    assertThrows(BusinessException.class, () -> optionUCC.addOption(1, 2, buyer));
  }

  @DisplayName("test addOption bad user only")
  @Test
  public void addOptionBadUserTest() {
    UserDTO buyer = userFactory.getUser();
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    furniture.setCondition(ValueLink.FurnitureCondition.en_vente);
    assertThrows(BusinessException.class, () -> optionUCC.addOption(1, 2, buyer));
    Mockito.when(daoUser.getUserById(1)).thenReturn(buyer);
    buyer.setValidRegistration(false);
    buyer.setId(1);
    assertThrows(BusinessException.class, () -> optionUCC.addOption(1, 2, buyer));
    buyer.setValidRegistration(true);
    buyer.setUserType(ValueLink.UserType.admin);
    assertThrows(BusinessException.class, () -> optionUCC.addOption(1, 2, buyer));
  }

  @DisplayName("test addOption bad duration only")
  @Test
  public void addOptionBadDurationTest() {
    UserDTO buyer = userFactory.getUser();
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    Mockito.when(daoUser.getUserById(1)).thenReturn(buyer);
    furniture.setCondition(ValueLink.FurnitureCondition.en_vente);
    buyer.setValidRegistration(true);
    buyer.setUserType(ValueLink.UserType.client);
    assertThrows(BusinessException.class, () -> optionUCC.addOption(1, -1, buyer));
    assertThrows(BusinessException.class, () -> optionUCC.addOption(1, 6, buyer));
    OptionDTO optionToAdd = optionFactory.getOption();
    Mockito.when(daoOption.addOption(optionToAdd)).thenReturn(1);
    optionToAdd.setStatus(ValueLink.OptionStatus.en_cours);
    optionToAdd.setDuration(5);
    assertThrows(BusinessException.class, () -> optionUCC.addOption(1, 1, buyer));
  }

  @DisplayName("test cancelOption good parameters")
  @Test
  public void cancelOptionAllGoodTest() {
    // TODO essayer de faire changer optionstatus apres daoOpt.cancelOpt dans mockito
    UserDTO buyer = userFactory.getUser();
    Mockito.when(daoUser.getUserById(1)).thenReturn(buyer);
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    OptionDTO optionToAdd = optionFactory.getOption();
    List<OptionDTO> listPreviousOption = new ArrayList<OptionDTO>();
    listPreviousOption.add(optionToAdd);
    assertTrue(listPreviousOption.size() != 0);
    assertTrue(listPreviousOption.contains(optionToAdd));
    Mockito.when(daoOption.selectOptionsOfFurniture(1)).thenReturn(listPreviousOption);
    optionToAdd.setStatus(ValueLink.OptionStatus.en_cours);
    optionToAdd.setBuyer(buyer);
    // Mockito.when(daoOption.cancelOption(optionToAdd)).thenReturn(true);
    // Mockito.when(daoFurniture.updateCondition(1, ValueLink.FurnitureCondition.en_vente.ordinal()))
    // .thenReturn(true);
    optionUCC.cancelOption(1, buyer);
    optionToAdd.setStatus(ValueLink.OptionStatus.annulee);
    assertEquals(ValueLink.OptionStatus.annulee, optionToAdd.getStatus());
  }

  @DisplayName("test cancelOption bad furniture")
  @Test
  public void cancelOptionBadFurnitureTest() {
    UserDTO buyer = userFactory.getUser();
    // Mockito.when(daoUser.getUserById(1)).thenReturn(buyer);
    // FurnitureDTO furniture = furnitureFactory.getFurniture();
    // Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    OptionDTO optionToAdd = optionFactory.getOption();
    Mockito.when(daoOption.cancelOption(optionToAdd)).thenReturn(false);
    // optionToAdd.setBuyer(buyer);
    // "Il n'y a pas d'option sur ce meuble",
    assertThrows(BusinessException.class, () -> optionUCC.cancelOption(1, buyer));
    optionToAdd.setStatus(ValueLink.OptionStatus.finie);
    // "Il n'y a pas d'option en cours sur ce meuble",
    assertThrows(BusinessException.class, () -> optionUCC.cancelOption(1, buyer));
  }


  @DisplayName("test cancelOption bad user")
  @Test
  public void cancelOptionBadUserTest() {
    // TODO essayer de faire changer optionstatus apres daoOpt.cancelOpt dans mockito
    UserDTO buyer = userFactory.getUser();
    // buyer.setId(1);
    // UserDTO buyer2 = userFactory.getUser();
    // Mockito.when(daoUser.getUserById(2)).thenReturn(buyer2);
    // FurnitureDTO furniture = furnitureFactory.getFurniture();
    // Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    OptionDTO optionToAdd = optionFactory.getOption();
    // optionToAdd.setBuyer(buyer2);
    // optionToAdd.setStatus(ValueLink.OptionStatus.en_cours);
    Mockito.when(daoOption.cancelOption(optionToAdd)).thenReturn(false);
    // "Vous ne pouvez pas annuler l'option d'un autre utilisateur",
    assertThrows(BusinessException.class, () -> optionUCC.cancelOption(1, buyer));
  }


  @DisplayName("test cancelOptionAdmin good parameters")
  @Test
  public void cancelOptionAdminAllGoodTest() {
    // TODO essayer de faire changer optionstatus apres daoOpt.cancelOptAdmin dans mockito
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    OptionDTO optionToAdd = optionFactory.getOption();
    List<OptionDTO> listPreviousOption = new ArrayList<OptionDTO>();
    listPreviousOption.add(optionToAdd);
    assertTrue(listPreviousOption.size() != 0);
    assertTrue(listPreviousOption.contains(optionToAdd));
    Mockito.when(daoOption.selectOptionsOfFurniture(1)).thenReturn(listPreviousOption);
    optionToAdd.setStatus(ValueLink.OptionStatus.en_cours);
    // Mockito.when(daoOption.cancelOption(optionToAdd)).thenReturn(true);
    // Mockito.when(daoFurniture.updateCondition(1, ValueLink.FurnitureCondition.en_vente.ordinal()))
    // .thenReturn(true);
    Mockito.when(daoOption.cancelOption(optionToAdd)).thenReturn(true);
    optionUCC.cancelOptionByAdmin(1);
    optionToAdd.setStatus(ValueLink.OptionStatus.annulee);
    assertEquals(ValueLink.OptionStatus.annulee, optionToAdd.getStatus());
  }

  @DisplayName("test cancelOptionAdmin bad furniture")
  @Test
  public void cancelOptionAdminBadFurnitureTest() {
    OptionDTO optionToAdd = optionFactory.getOption();
    Mockito.when(daoOption.cancelOption(optionToAdd)).thenReturn(false);
    // "Il n'y a pas d'option en cours sur ce meuble",
    assertThrows(BusinessException.class, () -> optionUCC.cancelOptionByAdmin(1));
  }

  @DisplayName("test getLastOptionOfFurniture ")
  @Test
  public void getLastOptionOfFurnitureTest() {
    // TODO verifyOptionStatus ???
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    assertThrows(BusinessException.class, () -> optionUCC.getLastOptionOfFurniture(1));
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    OptionDTO option = optionFactory.getOption();
    option.setId(1);
    option.setStatus(ValueLink.OptionStatus.en_cours);
    option.setDuration(2);
    option.setDate(Date.from(Instant.now()));
    Mockito.when(daoOption.getLastOptionOfFurniture(1)).thenReturn(option);
    assertEquals(option, optionUCC.getLastOptionOfFurniture(1));
  }


}
