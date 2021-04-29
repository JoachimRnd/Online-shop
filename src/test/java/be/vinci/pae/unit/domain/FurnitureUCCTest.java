package be.vinci.pae.unit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Instant;
import java.time.LocalDate;
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
import be.vinci.pae.domain.furniture.FurnitureUCCImpl;
import be.vinci.pae.domain.picture.PictureFactory;
import be.vinci.pae.domain.type.TypeDTO;
import be.vinci.pae.domain.type.TypeFactory;
import be.vinci.pae.domain.user.UserDTO;
import be.vinci.pae.domain.user.UserFactory;
import be.vinci.pae.domain.visitrequest.VisitRequestDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestFactory;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.services.type.DAOType;
import be.vinci.pae.services.user.DAOUser;
import be.vinci.pae.services.visitrequest.DAOVisitRequest;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.ValueLink;
import be.vinci.pae.utils.ValueLink.FurnitureCondition;

public class FurnitureUCCTest {

  FurnitureFactory furnitureFactory;

  UserFactory userFactory;

  TypeFactory typeFactory;

  PictureFactory pictureFactory;

  VisitRequestFactory vrFactory;

  @Mock
  DalServices dalServices;

  @Mock
  DAOFurniture daoFurniture;
  @Mock
  DAOUser daoUser;
  @Mock
  DAOType daoType;
  @Mock
  DAOVisitRequest daoVR;

  @InjectMocks
  FurnitureUCCImpl furnitureUCC;


  @BeforeEach
  void initAll() {
    MockitoAnnotations.initMocks(this);
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
    this.furnitureFactory = locator.getService(FurnitureFactory.class);
    this.userFactory = locator.getService(UserFactory.class);
    this.typeFactory = locator.getService(TypeFactory.class);
    // this.vrFactory = locator.getService(VisitRequestFactory.class);

  }

  @DisplayName("test if furniture is null")
  @Test
  public void furnitureUCCNotNullTest() {
    assertNotNull(this.furnitureUCC);
  }

  @DisplayName("test addFurniture good parameters")
  @Test
  public void addFurnitureAllGoodTest() {
    FurnitureDTO furnitureToAdd = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.insertFurniture(furnitureToAdd)).thenReturn(1);
    FurnitureDTO furniture = furnitureUCC.addFurniture(furnitureToAdd);
    assertEquals(furnitureToAdd, furniture);
  }

  @DisplayName("test addFurniture bad furniture")
  @Test
  public void addFurnitureAllBadTest() {
    FurnitureDTO furnitureToAdd = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.insertFurniture(furnitureToAdd)).thenReturn(-1);
    FurnitureDTO furniture = furnitureUCC.addFurniture(furnitureToAdd);
    assertFalse(daoFurniture.selectAllFurniture().contains(furniture));
    assertEquals(-1, daoFurniture.insertFurniture(furniture));
  }

  @DisplayName("test modifyFurniture with good parameters")
  @Test
  public void modifyFurnitureAllGoodTest() {
    FurnitureCondition condition = ValueLink.FurnitureCondition.en_vente;
    FurnitureDTO furnitureToAdd = furnitureFactory.getFurniture();
    furnitureToAdd.setCondition(ValueLink.FurnitureCondition.retire_de_vente);
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furnitureToAdd);
    furnitureToAdd.setId(1);
    UserDTO user = userFactory.getUser();
    user.setId(1);
    // assertEquals(furnitureToAdd, furnitureUCC.getFurnitureById(1));
    // Mockito.when(daoFurniture.updateSellingPrice(1, 200.00)).thenReturn(true);
    Mockito.when(daoFurniture.updateSellingDate(1, Instant.now())).thenReturn(true);
    // Mockito.when(daoFurniture.updateWithdrawalDateToCustomer(1, null)).thenReturn(true);
    Mockito.when(daoFurniture.updateCondition(1, condition.ordinal())).thenReturn(true);
    Mockito.when(daoUser.getUserByEmail("test")).thenReturn(user);
    Mockito.when(daoFurniture.updateUnregisteredBuyerEmail(1, null)).thenReturn(true);
    assertEquals(true, furnitureUCC.modifyCondition(1, condition));


    condition = ValueLink.FurnitureCondition.en_magasin;
    furnitureToAdd.setCondition(ValueLink.FurnitureCondition.en_vente);
    // assertEquals(furnitureToAdd, furnitureUCC.getFurnitureById(1));
    // Mockito.when(daoFurniture.updateSellingPrice(1, 200.00)).thenReturn(true);
    Mockito.when(daoFurniture.updateDepositDate(1, Instant.now())).thenReturn(true);
    // Mockito.when(daoFurniture.updateWithdrawalDateToCustomer(1, null)).thenReturn(true);
    Mockito.when(daoFurniture.updateCondition(1, condition.ordinal())).thenReturn(true);
    Mockito.when(daoUser.getUserByEmail("test")).thenReturn(user);
    Mockito.when(daoFurniture.updateUnregisteredBuyerEmail(1, null)).thenReturn(true);
    Mockito.when(daoFurniture.updateWithdrawalDateToCustomer(1, LocalDate.now())).thenReturn(true);
    assertEquals(true, furnitureUCC.modifyCondition(1, condition));
  }

  @DisplayName("test modifyFurniture bad parameters")
  @Test
  public void modifyFurnitureAllBadTest() {
    FurnitureCondition condition = ValueLink.FurnitureCondition.ne_convient_pas;
    FurnitureDTO furnitureToAdd = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furnitureToAdd);
    Mockito.when(daoFurniture.updateSellingDate(furnitureToAdd.getId(), Instant.now()))
        .thenReturn(false);
    Mockito.when(daoFurniture.updateSellingPrice(furnitureToAdd.getId(), -100.00))
        .thenReturn(false);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifyCondition(-1, condition));
  }

  @DisplayName("test modifyFurniture only bad furniture")
  @Test
  public void modifyFurnitureBadFurnitureGoodTest() {
    FurnitureCondition condition = ValueLink.FurnitureCondition.ne_convient_pas;
    Mockito.when(daoFurniture.updateCondition(1, condition.ordinal())).thenReturn(false);
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(null);
    assertThrows(NullPointerException.class, () -> furnitureUCC.modifyCondition(1, condition));
  }

  @DisplayName("test getAllFurniture ")
  @Test
  public void getFurnitureTest() {
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    assertNotNull(furnitureUCC.getAllFurniture());
    assertTrue(furnitureUCC.getAllFurniture().isEmpty());
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    list.add(furniture);
    Mockito.when(daoFurniture.selectAllFurniture()).thenReturn(list);
    assertEquals(list, furnitureUCC.getAllFurniture());
    assertTrue(furnitureUCC.getAllFurniture().contains(furniture));
    assertEquals(1, furnitureUCC.getAllFurniture().size());
  }

  @DisplayName("test getFurnitureUsers ")
  @Test
  public void getFurnitureUsersTest() {
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    assertNotNull(furnitureUCC.getFurnitureUsers(1));
    assertTrue(furnitureUCC.getFurnitureUsers(1).isEmpty());
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    list.add(furniture);
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    Mockito.when(daoFurniture.selectFurnitureUsers(1)).thenReturn(list);
    assertEquals(list, furnitureUCC.getFurnitureUsers(1));
    assertTrue(furnitureUCC.getFurnitureUsers(1).contains(furniture));
    assertEquals(1, furnitureUCC.getFurnitureUsers(1).size());
  }

  @DisplayName("test getFurnitureById ")
  @Test
  public void getFurnitureByIdTest() {
    assertNull(furnitureUCC.getFurnitureById(1));
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    assertEquals(furniture, furnitureUCC.getFurnitureById(1));
  }

  @DisplayName("test modifyType")
  @Test
  public void modifyTypeTest() {
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    TypeDTO type = typeFactory.getType();
    Mockito.when(daoType.selectTypeById(1)).thenReturn(type);
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    Mockito.when(daoFurniture.updateType(1, 1)).thenReturn(true);
    assertTrue(furnitureUCC.modifyType(1, 1));
    Mockito.when(daoType.selectTypeById(1)).thenReturn(null);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifyType(1, 1));
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(null);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifyType(1, 1));
    Mockito.when(daoType.selectTypeById(1)).thenReturn(type);
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    Mockito.when(daoFurniture.updateType(1, 1)).thenReturn(false);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifyType(1, 1));
  }

  @DisplayName("test modifyPurchasePrice")
  @Test
  public void modifyPurchasePriceTest() {
    Mockito.when(daoFurniture.updatePurchasePrice(1, 200.00)).thenReturn(true);
    assertTrue(furnitureUCC.modifyPurchasePrice(1, 200));
    Mockito.when(daoFurniture.updatePurchasePrice(1, 200.00)).thenReturn(false);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifyPurchasePrice(1, 200));
  }

  @DisplayName("test modifySellingPrice")
  @Test
  public void modifySellingPriceTest() {
    Mockito.when(daoFurniture.updateSellingPrice(1, 200.00)).thenReturn(true);
    assertTrue(furnitureUCC.modifySellingPrice(1, 200));
    Mockito.when(daoFurniture.updateSellingPrice(1, 200.00)).thenReturn(false);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifySellingPrice(1, 200));
  }

  @DisplayName("test modifySpecialSalePrice")
  @Test
  public void modifySpecialSaleTest() {
    Mockito.when(daoFurniture.updateSpecialSalePrice(1, 200.00)).thenReturn(true);
    assertTrue(furnitureUCC.modifySpecialSalePrice(1, 200));
    Mockito.when(daoFurniture.updateSpecialSalePrice(1, 200.00)).thenReturn(false);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifySpecialSalePrice(1, 200));
  }

  @DisplayName("test modifyDescription")
  @Test
  public void modifyDescriptionTest() {
    Mockito.when(daoFurniture.updateDescription(1, "test")).thenReturn(true);
    assertTrue(furnitureUCC.modifyDescription(1, "test"));
    Mockito.when(daoFurniture.updateDescription(1, "test")).thenReturn(false);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifyDescription(1, "test"));
  }

  @DisplayName("test modifyWithdrawalDateToCustomer")
  @Test
  public void modifyWithdrawalDateToCustomerTest() {
    Mockito.when(daoFurniture.updateWithdrawalDateToCustomer(1, LocalDate.now())).thenReturn(true);
    assertTrue(furnitureUCC.modifyWithdrawalDateToCustomer(1, LocalDate.now()));
    Mockito.when(daoFurniture.updateWithdrawalDateToCustomer(1, LocalDate.now())).thenReturn(false);
    assertThrows(BusinessException.class,
        () -> furnitureUCC.modifyWithdrawalDateToCustomer(1, LocalDate.now()));
  }

  @DisplayName("test modifyWithdrawalDateFromCustomer")
  @Test
  public void modifyWithdrawalDateFromCustomerTest() {
    Mockito.when(daoFurniture.updateWithdrawalDateFromCustomer(1, LocalDate.now()))
        .thenReturn(true);
    assertTrue(furnitureUCC.modifyWithdrawalDateFromCustomer(1, LocalDate.now()));
    Mockito.when(daoFurniture.updateWithdrawalDateFromCustomer(1, LocalDate.now()))
        .thenReturn(false);
    assertThrows(BusinessException.class,
        () -> furnitureUCC.modifyWithdrawalDateFromCustomer(1, LocalDate.now()));
  }

  @DisplayName("test modifyDeliveryDate")
  @Test
  public void modifyDeliveryDateTest() {
    Mockito.when(daoFurniture.updateDeliveryDate(1, LocalDate.now())).thenReturn(true);
    assertTrue(furnitureUCC.modifyDeliveryDate(1, LocalDate.now()));
    Mockito.when(daoFurniture.updateDeliveryDate(1, LocalDate.now())).thenReturn(false);
    assertThrows(BusinessException.class,
        () -> furnitureUCC.modifyDeliveryDate(1, LocalDate.now()));
  }

  @DisplayName("test modifyBuyerEmail")
  @Test
  public void modifyBuyerEmailTest() {
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    UserDTO buyer = userFactory.getUser();
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    Mockito.when(daoUser.getUserById(1)).thenReturn(buyer);
    Mockito.when(daoUser.getUserByEmail("test@test.com")).thenReturn(buyer);
    Mockito.when(daoFurniture.updateBuyer(1, 1)).thenReturn(true);
    Mockito.when(daoFurniture.updateUnregisteredBuyerEmail(1, "test@test.com")).thenReturn(true);
    assertTrue(furnitureUCC.modifyBuyerEmail(1, "test@test.com"));
    Mockito.when(daoFurniture.updateDeliveryDate(1, LocalDate.now())).thenReturn(false);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifyBuyerEmail(1, "test@test.com"));
  }

  @DisplayName("test getPersonalFurnitureById")
  @Test
  public void getPersonalFurnitureByIdTest() {
    // TODO ajouter dans init() + faire en sorte que VR ait un dal
    FurnitureDTO furniture = furnitureFactory.getFurniture();
    UserDTO buyer = userFactory.getUser();
    VisitRequestDTO vr = vrFactory.getVisitRequest();
    Mockito.when(daoFurniture.selectFurnitureById(1)).thenReturn(furniture);
    Mockito.when(daoUser.getUserById(1)).thenReturn(buyer);
    buyer.setId(1);
    furniture.setBuyer(buyer);
    Mockito.when(daoVR.selectVisitRequestByUserAndFurniture(1, 1)).thenReturn(vr);
    assertEquals(furniture, furnitureUCC.getPersonalFurnitureById(1, buyer));
  }

  @DisplayName("test modifyFavouritePicture")
  @Test
  public void modifyFavouritePictureTest() {
    Mockito.when(daoFurniture.updateFavouritePicture(1, 1)).thenReturn(true);
    assertTrue(furnitureUCC.modifyFavouritePicture(1, 1));
    Mockito.when(daoFurniture.updateFavouritePicture(1, 1)).thenReturn(false);
    assertThrows(BusinessException.class, () -> furnitureUCC.modifyFavouritePicture(1, 1));
  }

  @DisplayName("test getSalesFurnitureAdmin")
  @Test
  public void getSalesFurnitureAdminTest() {
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    assertNotNull(furnitureUCC.getSalesFurnitureAdmin());
    assertTrue(furnitureUCC.getSalesFurnitureAdmin().isEmpty());
    Mockito.when(daoFurniture.selectSalesFurniture()).thenReturn(list);
    assertEquals(list, furnitureUCC.getSalesFurnitureAdmin());
  }

  @DisplayName("test getFurnituresFiltered")
  @Test
  public void getFurnituresFilteredTest() {
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    assertNotNull(furnitureUCC.getFurnituresFiltered("test", 200.00, "usernameTest"));
    assertTrue(furnitureUCC.getFurnituresFiltered("test", 200.00, "usernameTest").isEmpty());
    Mockito.when(daoFurniture.selectFurnituresFiltered("test", 200.00, "usernameTest"))
        .thenReturn(list);
    assertEquals(list, furnitureUCC.getFurnituresFiltered("test", 200.00, "usernameTest"));
  }

  @DisplayName("test getFurnitureBuyBy")
  @Test
  public void getFurnitureBuyByTest() {
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    assertNotNull(furnitureUCC.getFurnitureBuyBy(1));
    assertTrue(furnitureUCC.getFurnitureBuyBy(1).isEmpty());
    Mockito.when(daoFurniture.getFurnitureBuyBy(1)).thenReturn(list);
    assertEquals(list, furnitureUCC.getFurnitureBuyBy(1));
  }

  @DisplayName("test getFurnitureSellBy")
  @Test
  public void getFurnitureSellByTest() {
    List<FurnitureDTO> list = new ArrayList<FurnitureDTO>();
    assertNotNull(furnitureUCC.getFurnitureSellBy(1));
    assertTrue(furnitureUCC.getFurnitureSellBy(1).isEmpty());
    Mockito.when(daoFurniture.getFurnitureSellBy(1)).thenReturn(list);
    assertEquals(list, furnitureUCC.getFurnitureSellBy(1));
  }

}
