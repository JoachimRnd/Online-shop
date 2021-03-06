package be.vinci.pae.unit.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import be.vinci.pae.domain.UserUCC;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserUCCTest {

  private UserUCC userUCC;

  @BeforeEach
  void initAll() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
    this.userUCC = locator.getService(UserUCC.class);
  }

  @Test
  public void userUCCNotNullTest() {
    assertNotNull(this.userUCC);
  }

  @Test
  public void loginGoodLoginGoodPasswordTest() {
    assertNotNull(userUCC.login("login", "password"));
  }

  @Test
  public void loginBadLoginGoodPasswordTest() {
    assertThrows(IllegalArgumentException.class, () -> userUCC.login("bad", "password"));
  }

  @Test
  public void loginGoodLoginBadPasswordTest() {
    assertThrows(IllegalArgumentException.class, () -> userUCC.login("login", "bad"));
  }
}
