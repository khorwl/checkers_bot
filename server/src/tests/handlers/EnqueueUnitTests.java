package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.sessions.SessionServerException;
import core.userdb.User;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.handlers.enqueue.Enqueue;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.Pair;

public class EnqueueUnitTests extends HandlerTestCase {

  private Enqueue handler;

  @BeforeEach
  public void init() {
    handler = new Enqueue(queryParser, server);
  }

  @Test
  public void handleRequest_invalidNameParameter_shouldReturnInvalidRequestAnswer() {
    var request = new HttpRequest("", Map.of("n@ame", "lalka"));
    var expected = Response.createInvalidRequest("Invalid query", false);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_noSuchUser_shouldReturnRightResponse() {
    var request = new HttpRequest("", Map.of("name", "username"));
    var expected = Response.createSuccess("No such user: username", false);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_enquingAreFailed_shouldReturnRightResponse() {
    var user = new User("user");
    var request = new HttpRequest("", Map.of("name", "user"));
    var expected =Response.createSuccess(String.format("Cant enqueue user %s", user), false);
    when(userDataBase.getUserOrNull(any())).thenReturn(user);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_enquedSucessfully_shouldReturnRightResponse() {
    var user = new User("user");
    var request = new HttpRequest("", Map.of("name", "user"));
    var expected = Response.createSuccess(String.format("Successfully enqueued user %s", user), true);
    when(userDataBase.getUserOrNull(any())).thenReturn(user);
    when(playerQueue.enqueue(any())).thenReturn(true);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_userAlreadyHaveSession_shouldReturnRightResponse() {
    var user = new User("user");
    var request = new HttpRequest("", Map.of("name", "user"));
    var expected = Response.createSuccess("User user already have session", false);
    when(userDataBase.getUserOrNull(any())).thenReturn(user);
    when(sessionServer.hasSessionWithUser(any())).thenReturn(true);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_shouldMakeAttemptOfDequeuingPair() {
    var request = new HttpRequest("", Map.of("name", "user"));
    handler.handleRequest(request);

    verify(playerQueue, only()).dequeuePairOrNull();
  }

  @Test
  public void handleRequest_ifSuccessfullyDequedPair_shouldCallcreateSession()
      throws SessionServerException {
    var user1 = new User("user1");
    var user2 = new User("user2");
    when(playerQueue.dequeuePairOrNull()).thenReturn(Pair.create(user1, user2));
    handler.handleRequest(new HttpRequest("", Map.of()));

    verify(sessionServer, only()).createSession(user1, user2);
  }
}
