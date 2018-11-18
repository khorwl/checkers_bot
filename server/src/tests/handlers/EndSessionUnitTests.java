package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import core.sessions.Session;
import core.sessions.SessionServer;
import core.sessions.SessionServerException;
import core.userdb.User;
import core.userdb.UserDataBaseException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.handlers.end_session.EndSession;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;

public class EndSessionUnitTests extends HandlerTestCase {
  private EndSession handler;

  @BeforeEach
  public void init() {
    handler = new EndSession(queryParser, server);
  }

  @Test
  public void handleRequest_withInvalidName_shouldThrowNoThatKeyException() {
    var request = new HttpRequest("", Map.of());

    assertThrows(NoThatParameterException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_withUnExistingUser_shouldThrowUserDataBaseException()
      throws UserDataBaseException {
    var request = new HttpRequest("", Map.of("name", "roma"));
    when(userDataBase.getUser(any())).thenThrow(new UserDataBaseException());

    assertThrows(UserDataBaseException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_withNoSessionForUser_shouldThrowSessionServerException()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var user = new User("roma");
    var request = new HttpRequest("", Map.of("name", "roma"));
    when(userDataBase.getUser(any())).thenReturn(user);
    when(sessionServer.getSessionWithUser(user)).thenThrow(new SessionServerException());

    assertThrows(SessionServerException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_withExistingSession_shouldReturnRightResponse()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var user = new User("roma");
    var session = new Session("my", null);
    var request = new HttpRequest("", Map.of("name", "roma"));
    var expected = Response.createSuccess("Successfully ended session my", true);
    when(userDataBase.getUser(any())).thenReturn(user);
    when(sessionServer.getSessionWithUser(user)).thenReturn(session);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }
}
