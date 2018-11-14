package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import core.userdb.User;
import core.userdb.UserDataBaseException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpStatusCode;
import server.Request;
import server.Response;
import server.handlers.HaveSession;

public class HaveSessionUnitTests extends HandlerTestCase {

  private HaveSession handler;

  @BeforeEach
  public void init() {
    handler = new HaveSession(queryParser, server);
  }

  @Test
  public void handleRequest_withoutUserName_shouldReturnInvalidQuery() {
    var request = new Request("", Map.of("NAME", "MA<E"));
    var expected = new Response("Invalid query", HttpStatusCode.BadRequest);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_noSuchUser_shouldReturnBadRequest() throws UserDataBaseException {
    var request = new Request("", Map.of("name", "username"));
    var expected = new Response(String.format("No such user: \"%s\"", "username"), HttpStatusCode.BadRequest);
    when(userDataBase.getUser("username")).thenThrow(new UserDataBaseException());

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_noSessionForUser_shouldReturnFalse() throws UserDataBaseException {
    var request = new Request("", Map.of("name", "user"));
    var expected = new Response("false", HttpStatusCode.OK);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withSessionForThatUser_shouldReturnTrue() {
    var request = new Request("", Map.of("name", "user"));
    var expected = new Response("true", HttpStatusCode.OK);
    when(sessionServer.hasSessionWithUser(any())).thenReturn(true);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }
}
