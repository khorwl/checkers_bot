package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import core.userdb.User;
import java.util.HashSet;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpStatusCode;
import server.Request;
import server.Response;
import server.handlers.Delete;

public class DeleteUnitTests extends HandlerTestCase {

  private Delete delete;

  @BeforeEach
  private void init() {
    delete = new Delete(queryParser, server);
  }

  @Test
  public void handleRequest_withInvalidQuery_shouldReturnInvalidQueryResponse() {
    var request = new Request(null, Map.of("Name", "user"));
    var expected = new Response("Invalid query", HttpStatusCode.BadRequest);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withUnexistingUser_shouldReturnFail() {
    var request = new Request(null, Map.of("name", "user"));
    var expected = new Response("false", HttpStatusCode.OK);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingUserButThisUserHaveSession_shouldReturnFail() {
    when(sessionServer.hasSessionWithUser(any())).thenReturn(true);
    var request = new Request(null, Map.of("name", "user"));
    var expected = new Response("false", HttpStatusCode.OK);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingUserAndNoSessions_shouldReturnSuccess() {
    var user = new User("name");
    when(userDataBase.getUserOrNull(any())).thenReturn(user);
    when(userDataBase.delete(any())).thenReturn(true);
    var request = new Request(null, Map.of("name", "name"));
    var expected = new Response("true", HttpStatusCode.OK);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }
}
