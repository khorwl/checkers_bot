package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import core.userdb.User;
import core.userdb.UserDataBaseException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.http.HttpResponse;
import server.api.http.HttpStatusCode;
import server.api.http.HttpRequest;
import server.api.handlers.delete.Delete;
import server.api.response.Response;

public class DeleteUnitTests extends HandlerTestCase {

  private Delete delete;

  @BeforeEach
  private void init() {
    delete = new Delete(queryParser, server);
  }

  @Test
  public void handleRequest_withInvalidQuery_shouldReturnInvalidQueryResponse() {
    var request = new HttpRequest(null, Map.of("Name", "user"));
    var expected = Response.createInvalidRequest();

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withUnexistingUser_shouldReturnNoSuchUser() {
    var request = new HttpRequest(null, Map.of("name", "user"));
    var expected =  Response.createSuccess("No such user", false);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingUserButThisUserHaveSession_shouldReturnFail()
      throws UserDataBaseException {
    var user = new User("user");
    when(userDataBase.getUserOrNull(any())).thenReturn(user);
    when(sessionServer.hasSessionWithUser(any())).thenReturn(true);
    var request = new HttpRequest(null, Map.of("name", "user"));
    var expected = Response.createSuccess("User have open session", false);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingUserAndNoSessions_shouldReturnSuccess() {
    var user = new User("name");
    when(userDataBase.getUserOrNull(any())).thenReturn(user);
    when(userDataBase.delete(any())).thenReturn(true);
    var request = new HttpRequest(null, Map.of("name", "name"));
    var expected = Response.createSuccess("Successfully delete user name", true);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }
}
