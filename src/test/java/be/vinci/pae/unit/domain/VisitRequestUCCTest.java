package be.vinci.pae.unit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
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
import be.vinci.pae.domain.visitrequest.VisitRequestDTO;
import be.vinci.pae.domain.visitrequest.VisitRequestFactory;
import be.vinci.pae.domain.visitrequest.VisitRequestUCCImpl;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.services.visitrequest.DAOVisitRequest;

public class VisitRequestUCCTest {

  VisitRequestFactory vrFactory;

  @Mock
  DalServices dalServices;

  @Mock
  DAOVisitRequest daoVR;
  @Mock
  DAOFurniture daoFurniture;

  @InjectMocks
  VisitRequestUCCImpl vrUCC;


  @BeforeEach
  void initAll() {
    MockitoAnnotations.initMocks(this);
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
    this.vrFactory = locator.getService(VisitRequestFactory.class);

  }

  @DisplayName("test if VR is null")
  @Test
  public void VRUCCNotNullTest() {
    assertNotNull(this.vrUCC);
  }

  @DisplayName("test addVisitRequest")
  @Test
  public void addVisitRequestTest() {
    // TODO

  }

  @DisplayName("test getAllVisitsOpenned")
  @Test
  public void getAllVisitsOpennedTest() {
    List<VisitRequestDTO> list = new ArrayList<VisitRequestDTO>();
    assertNotNull(vrUCC.getAllVisitsOpenned());
    assertTrue(vrUCC.getAllVisitsOpenned().isEmpty());
    Mockito.when(daoVR.getAllVisitsOpenned()).thenReturn(list);
    assertEquals(list, vrUCC.getAllVisitsOpenned());
  }

  @DisplayName("test getVisitRequestById")
  @Test
  public void getVisitRequestByIdTest() {
    VisitRequestDTO vr = vrFactory.getVisitRequest();
    Mockito.when(daoVR.selectVisitRequestById(1)).thenReturn(vr);
    assertEquals(vr, vrUCC.getVisitRequestById(1));
    Mockito.when(daoVR.selectVisitRequestById(1)).thenReturn(null);
    assertNull(vrUCC.getVisitRequestById(1));
  }

  @DisplayName("test modifyVisitRequest")
  @Test
  public void modifyVisitRequestTest() {
    VisitRequestDTO vr = vrFactory.getVisitRequest();
    Mockito.when(daoVR.cancelVisitRequest(1, "test")).thenReturn(true);
    Mockito.when(daoFurniture.refuseAllFurnitureByVisitId(1)).thenReturn(true);
    assertEquals("annulee", vrUCC.modifyVisitRequest(1, "test", "2021-04-01"));
    Mockito.when(daoVR.cancelVisitRequest(1, "test")).thenReturn(false);
    Mockito.when(daoFurniture.refuseAllFurnitureByVisitId(1)).thenReturn(false);
    assertEquals(null, vrUCC.modifyVisitRequest(1, "test", "2021-04-01"));
    Mockito
        .when(daoVR.chooseDateForVisit(1,
            Timestamp.valueOf(LocalDate.parse("2021-04-01").atTime(LocalTime.NOON))))
        .thenReturn(true);
    assertEquals("confirmee", vrUCC.modifyVisitRequest(1, "test", "2021-04-01"));
    Mockito
        .when(daoVR.chooseDateForVisit(1,
            Timestamp.valueOf(LocalDate.parse("2021-04-01").atTime(LocalTime.NOON))))
        .thenReturn(false);
    assertEquals(null, vrUCC.modifyVisitRequest(1, "test", "2021-04-01"));
    Mockito.when(daoVR.cancelVisitRequest(1, "test")).thenReturn(true);
    Mockito.when(daoFurniture.refuseAllFurnitureByVisitId(1)).thenReturn(true);
    Mockito
        .when(daoVR.chooseDateForVisit(1,
            Timestamp.valueOf(LocalDate.parse("2021-04-01").atTime(LocalTime.NOON))))
        .thenReturn(true);
    assertEquals(null, vrUCC.modifyVisitRequest(1, null, null));
  }
}
