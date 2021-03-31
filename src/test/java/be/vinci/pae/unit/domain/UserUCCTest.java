package be.vinci.pae.unit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.domain.UserUCCImpl;
import be.vinci.pae.services.DAOUser;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.utils.BusinessException;

public class UserUCCTest {


  UserFactory userFact;

  @Mock
  DalServices dalServices;

  @Mock
  DAOUser userdao;

  @InjectMocks
  UserUCCImpl userUCC;


  @BeforeEach
  void initAll() {
    MockitoAnnotations.initMocks(this);
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
    // this.userUCC = locator.getService(UserUCC.class);
    this.userFact = locator.getService(UserFactory.class);

  }

  @DisplayName("test if user is null")
  @Test
  public void userUCCNotNullTest() {
    assertNotNull(this.userUCC);
  }

  @DisplayName("test login with both good username and password")
  @Test
  public void loginGoodLoginGoodPasswordTest() {

    UserDTO userToConnect = userFact.getUser();
    userToConnect.setPassword(((User) userToConnect).hashPassword("test"));
    userToConnect.setUsername("test");

    Mockito.when(userdao.getUserByUsername("test")).thenReturn(userToConnect);
    UserDTO user = userUCC.login("test", "test");
    assertEquals(userToConnect, user);
  }

  @DisplayName("test login with bad username and good password")
  @Test
  public void loginBadLoginGoodPasswordTest() {
    UserDTO userToConnect = userFact.getUser();
    userToConnect.setPassword(((User) userToConnect).hashPassword("test"));
    userToConnect.setUsername("test");
    Mockito.when(userdao.getUserByUsername("test")).thenReturn(userToConnect);
    assertThrows(BusinessException.class, () -> userUCC.login("chouquettes", "test"));
  }

  @DisplayName("test login with good username and bad password")
  @Test
  public void loginGoodLoginBadPasswordTest() {
    UserDTO userToConnect = userFact.getUser();
    userToConnect.setPassword(((User) userToConnect).hashPassword("test"));
    userToConnect.setUsername("test");

    Mockito.when(userdao.getUserByUsername("test")).thenReturn(userToConnect);
    assertThrows(BusinessException.class, () -> userUCC.login("login", "bad"));
  }

  // TODO TESTER methodes register() et validate()

}
