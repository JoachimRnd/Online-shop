package be.vinci.pae.unit.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import be.vinci.pae.domain.visitrequest.VisitRequestFactory;
import be.vinci.pae.domain.visitrequest.VisitRequestUCCImpl;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.visitrequest.DAOVisitRequest;

public class VisitRequestUCCTest {

  VisitRequestFactory vrFactory;

  @Mock
  DalServices dalServices;

  @Mock
  DAOVisitRequest daoVR;

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
}
