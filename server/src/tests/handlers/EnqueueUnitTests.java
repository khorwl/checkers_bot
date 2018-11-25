package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.sessions.SessionServerException;
import core.userdb.User;
import core.userdb.UserDataBaseException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.handlers.enqueue.Enqueue;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import tools.Pair;

public class EnqueueUnitTests extends HandlerTestCase {

  private Enqueue handler;

  @BeforeEach
  public void init() {
    handler = new Enqueue(queryParser, server);
  }

  @Test
  public void handleRequest_invalidNameParameter_shouldThrowNoThatParameterException()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var request = new HttpRequest("", Map.of("n@ame", "lalka"));

    assertThrows(NoThatParameterException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_noSuchUser_shouldThrowUserDataBaseException()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var request = new HttpRequest("", Map.of("name", "username"));
    when(userDataBase.getUser(any())).thenThrow(new UserDataBaseException());

    assertThrows(UserDataBaseException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_enquingAreFailed_shouldReturnRightResponse()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var user = new User("user");
    var request = new HttpRequest("", Map.of("name", "user"));
    var expected =Response.createSuccess(String.format("Cant enqueue user %s", user), false);
    when(userDataBase.getUserElseNull(any())).thenReturn(user);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_enquedSucessfully_shouldReturnRightResponse()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var user = new User("user");
    var request = new HttpRequest("", Map.of("name", "user"));
    var expected = Response.createSuccess(String.format("Successfully enqueued user %s", user), true);
    when(userDataBase.getUserElseNull(any())).thenReturn(user);
    when(playerQueue.enqueue(any())).thenReturn(true);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_userAlreadyHaveSession_shouldReturnRightResponse()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var user = new User("user");
    var request = new HttpRequest("", Map.of("name", "user"));
    var expected = Response.createSuccess("User user already have session", false);
    when(userDataBase.getUserElseNull(any())).thenReturn(user);
    when(sessionServer.hasSessionWithUser(any())).thenReturn(true);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_shouldMakeAttemptOfDequeuingPair()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var request = new HttpRequest("", Map.of("name", "user"));
    when(userDataBase.getUser(any())).thenReturn(new User("user"));

    handler.handleRequest(request);

    verify(playerQueue, times(1)).dequeuePairElseNull();
  }

  @Test
  public void handleRequest_ifSuccessfullyDequedPair_shouldCallcreateSession()
      throws SessionServerException, NoThatParameterException, UserDataBaseException {
    var user1 = new User("user1");
    var user2 = new User("user2");
    when(playerQueue.dequeuePairElseNull()).thenReturn(Pair.create(user1, user2));
    when(userDataBase.getUser(any())).thenReturn(user1);

    handler.handleRequest(new HttpRequest("", Map.of("name", "user1")));

    verify(sessionServer, times(1)).createSession(user1, user2);
  }
}
