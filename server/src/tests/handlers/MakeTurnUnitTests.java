package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import core.checkers.primitives.Vector;
import core.userdb.User;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.handlers.make_turn.MakeTurn;
import server.api.http.HttpRequest;
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
  public void handleRequest_withoutName_shouldReturnInvalidRequest() {
    var request = new HttpRequest("", Map.of("nAme", "lol", "from", "lal", "to", "(1,2,3)"));

    assertEquals(handler.handleRequest(request).getCode(), ResponseCode.INVALID_REQUEST);
  }

  @Test
  public void handleRequest_withoutFrom_shouldReturnInvalidRequest() {
    var request = new HttpRequest("",
        Map.of("name", "roma", "to", Vector.create(1, 2, 3).toString()));

    assertEquals(handler.handleRequest(request).getCode(), ResponseCode.INVALID_REQUEST);
  }

  @Test
  public void handleRequest_withoutTo_shouldReturnInvalidRequest() {
    var request = new HttpRequest("",
        Map.of("name", "roma", "from", Vector.create(1, 2, 3).toString()));

    assertEquals(handler.handleRequest(request).getCode(), ResponseCode.INVALID_REQUEST);
  }

  @Test
  public void handleRequest_withInvalidFrom_shouldReturnInvalidRequest() {
    var request = new HttpRequest("",
        Map.of("name", "roma", "from", "not_a_vector", "to", Vector.create(1, 23, 4).toString()));

    assertEquals(handler.handleRequest(request).getCode(), ResponseCode.INVALID_REQUEST);
  }

  @Test
  public void handleRequest_withInvalidTo_shouldReturnInvalidRequest() {
    var request = new HttpRequest("",
        Map.of("name", "roma", "to", "not_a_vector", "from", Vector.create(1, 23, 4).toString()));

    assertEquals(handler.handleRequest(request).getCode(), ResponseCode.INVALID_REQUEST);
  }

  @Test
  public void handleRequest_withUnExistingUser_shouldReturnRightResponse() {
    var expected = Response.createSuccess("No such user: roma", null);

    var sut = handler.handleRequest(validRequest);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingUserWithoutSession_shouldReturnRightResponse() {
    var expected = Response.createSuccess("No session with user roma", null);
    when(userDataBase.getUserOrNull(user.getName())).thenReturn(user);

    var sut = handler.handleRequest(validRequest);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withExistingSession_shouldReturnResultOfGamesMakeTurn() {

  }
}
