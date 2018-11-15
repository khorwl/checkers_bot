package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import core.userdb.User;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.http.HttpResponse;
import server.api.http.HttpStatusCode;
import server.api.http.HttpRequest;
import server.api.handlers.Delete;

public class DeleteUnitTests extends HandlerTestCase {

  private Delete delete;

  @BeforeEach
  private void init() {
    delete = new Delete(queryParser, server);
  }

  @Test
  public void handleRequest_withInvalidQuery_shouldReturnInvalidQueryResponse() {
    var request = new HttpRequest(null, Map.of("Name", "user"));
    var expected = new HttpResponse("Invalid query", HttpStatusCode.BadRequest);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withUnexistingUser_shouldReturnFail() {
    var request = new HttpRequest(null, Map.of("name", "user"));
    var expected = new HttpResponse("false", HttpStatusCode.OK);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingUserButThisUserHaveSession_shouldReturnFail() {
    when(sessionServer.hasSessionWithUser(any())).thenReturn(true);
    var request = new HttpRequest(null, Map.of("name", "user"));
    var expected = new HttpResponse("false", HttpStatusCode.OK);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingUserAndNoSessions_shouldReturnSuccess() {
    var user = new User("name");
    when(userDataBase.getUserOrNull(any())).thenReturn(user);
    when(userDataBase.delete(any())).thenReturn(true);
    var request = new HttpRequest(null, Map.of("name", "name"));
    var expected = new HttpResponse("true", HttpStatusCode.OK);

    var sut = delete.handleRequest(request);

    assertEquals(expected, sut);
  }
}
