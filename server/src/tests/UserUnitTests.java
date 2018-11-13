package tests;

import core.userdb.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTests {

  @Test
  public void equals_null_shouldReturnFalse() {
    var u = new User("lol", player);

    var sut = u.equals(null);

    assertFalse(sut);
  }

  @Test
  public void equals_toNonUserObject_shouldReturnFalse() {
    var u = new User("lol", player);

    var sut = u.equals("lol");

    assertFalse(sut);
  }

  @Test
  public void equals_sameName_shouldReturnTrue() {
    var u1 = new User("user", player);
    var u2 = new User("user", player);

    var sut = u1.equals(u2);

    assertTrue(sut);
  }

  @Test
  public void equals_DifferentNames_shouldReturnFalse() {
    var u1 = new User("lol1", player);
    var u2 = new User("lol2", player);

    var sut = u1.equals(u2);

    assertFalse(sut);
  }
}
