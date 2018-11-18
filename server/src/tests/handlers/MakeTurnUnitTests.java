package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import core.checkers.primitives.Vector;
import core.sessions.SessionServerException;
import core.userdb.User;
import core.userdb.UserDataBaseException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.handlers.make_turn.MakeTurn;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import server.api.response.ResponseCode;

public class MakeTurnUnitTests extends HandlerTestCase {

  private MakeTurn handler;
  private Vector from;
  private Vector to;
  private HttpRequest validRequest;
  private User user;

  @BeforeEach
  public void init() {
    handler = new MakeTurn(queryParser, server);
    from = Vector.create(1, 2, 3);
    to = Vector.create(5, 6, 7);
    user = new User("roma");
    validRequest = new HttpRequest("", Map.of("name", user.getName(), "from", from.toString(), "to", to.toString()));
  }

  @Test
  public void handleRequest_withoutName_shouldThrowNoThatParameterException() {
    var request = new HttpRequest("", Map.of("nAme", "lol", "from", "lal", "to", "(1,2,3)"));

    assertThrows(NoThatParameterException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_withoutFrom_shouldThrowNoThatParameterException() {
    var request = new HttpRequest("",
        Map.of("name", "roma", "to", Vector.create(1, 2, 3).toString()));

    assertThrows(NoThatParameterException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_withoutTo_shouldReturnInvalidRequest() {
    var request = new HttpRequest("",
        Map.of("name", "roma", "from", Vector.create(1, 2, 3).toString()));

    assertThrows(NoThatParameterException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_withInvalidFrom_shouldReturnInvalidRequest()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var request = new HttpRequest("",
        Map.of("name", "roma", "from", "not_a_vector", "to", Vector.create(1, 23, 4).toString()));

    assertEquals(handler.handleRequest(request).getCode(), ResponseCode.INVALID_REQUEST);
  }

  @Test
  public void handleRequest_withInvalidTo_shouldReturnInvalidRequest()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var request = new HttpRequest("",
        Map.of("name", "roma", "to", "not_a_vector", "from", Vector.create(1, 23, 4).toString()));

    assertEquals(handler.handleRequest(request).getCode(), ResponseCode.INVALID_REQUEST);
  }

  @Test
  public void handleRequest_withUnExistingUser_shouldThrowUserDataBaseException()
      throws UserDataBaseException {
    when(userDataBase.getUser("roma")).thenThrow(new UserDataBaseException());

    assertThrows(UserDataBaseException.class, () -> handler.handleRequest(validRequest));
  }

  @Test
  public void handleRequest_withExistingUserWithoutSession_shouldThrowSessionServerException()
      throws SessionServerException, UserDataBaseException {
    when(userDataBase.getUser(user.getName())).thenReturn(user);
    when(sessionServer.getSessionWithUser(any())).thenThrow(new SessionServerException());

    assertThrows(SessionServerException.class, () -> handler.handleRequest(validRequest));
  }

  @Test
  public void handleRequest_withExistingSession_shouldReturnResultOfGamesMakeTurn() {

  }
}
