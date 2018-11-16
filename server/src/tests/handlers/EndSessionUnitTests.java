package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import core.sessions.Session;
import core.userdb.User;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.handlers.end_session.EndSession;
import server.api.http.HttpRequest;
import server.api.response.Response;

public class EndSessionUnitTests extends HandlerTestCase {
  private EndSession handler;

  @BeforeEach
  public void init() {
    handler = new EndSession(queryParser, server);
  }

  @Test
  public void handleRequest_withInvalidName_shouldReturnInvalidRequest() {
    var request = new HttpRequest("", Map.of());
    var expected = Response.createInvalidRequest("Invalid query", false);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withUnExistingUser_shouldReturnRightResponse() {
    var request = new HttpRequest("", Map.of("name", "roma"));
    var expected = Response.createSuccess("No that user: roma", false);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withNoSessionForUser_shouldReturnRightResponse() {
    var user = new User("roma");
    var request = new HttpRequest("", Map.of("name", "roma"));
    var expected = Response.createSuccess("No any session with user", false);
    when(userDataBase.getUserOrNull(any())).thenReturn(user);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingSession_shouldReturnRightResponse() {
    var user = new User("roma");
    var session = new Session("my", null);
    var request = new HttpRequest("", Map.of("name", "roma"));
    var expected = Response.createSuccess("Successfully ended session my", true);
    when(userDataBase.getUserOrNull(any())).thenReturn(user);
    when(sessionServer.getSessionWithUserOrNull(user)).thenReturn(session);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }
}
