package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import core.userdb.User;
import core.userdb.UserDataBaseException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.http.HttpRequest;
import server.api.handlers.delete.Delete;
import server.api.http.NoThatParameterException;
import server.api.response.Response;

public class DeleteUnitTests extends HandlerTestCase {

  private Delete delete;

  @BeforeEach
  private void init() {
    delete = new Delete(queryParser, server);
  }

  @Test
  public void handleRequest_withInvalidQuery_shouldThrowNoThatKeyException() {
    var request = new HttpRequest(null, Map.of("Name", "user"));

    assertThrows(NoThatParameterException.class, () -> delete.handleRequest(request));
  }

  @Test
  public void handleRequest_withUnexistingUser_shouldThrowUserDataBaseException()
      throws UserDataBaseException {
    var request = new HttpRequest(null, Map.of("name", "user"));
    when(userDataBase.getUser(any())).thenThrow(new UserDataBaseException());

    assertThrows(UserDataBaseException.class, () -> delete.handleRequest(request));
  }

  @Test
  public void handleRequest_withExistingUserButThisUserHaveSession_shouldReturnFail()
      throws UserDataBaseException, NoThatParameterException {
    var user = new User("user");
    when(userDataBase.getUserElseNull(any())).thenReturn(user);
    when(sessionServer.hasSessionWithUser(any())).thenReturn(true);
    var request = new HttpRequest(null, Map.of("name", "user"));
    var expected = Response.createSuccess("User have open session", false);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingUserAndNoSessions_shouldReturnSuccess()
      throws NoThatParameterException, UserDataBaseException {
    var user = new User("name");
    when(userDataBase.getUserElseNull(any())).thenReturn(user);
    when(userDataBase.delete(any())).thenReturn(true);
    var request = new HttpRequest(null, Map.of("name", "name"));
    var expected = Response.createSuccess("Successfully delete user name", true);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }
}
