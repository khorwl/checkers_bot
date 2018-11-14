package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpStatusCode;
import server.Request;
import server.Response;
import server.handlers.Register;

public class RegisterUnitTests extends HandlerTestCase {
  private Register register;

  @BeforeEach
  private void init() {
    register = new Register(queryParser, server);
  }

  @Test
  public void handleRequest_noNameParameter_shouldReturnBadRequest() {
    var request = new Request(null, Map.of("namE", "u$ser"));
    var expected = new Response("Invalid query", HttpStatusCode.BadRequest);

    var sut = register.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_registerOfUserDataBaseReturnsTrue_shouldReturnSuccessResponse() {
    when(userDataBase.register("username")).thenReturn(true);
    var request = new Request(null, Map.of("name", "username"));
    var expected = new Response("true", HttpStatusCode.OK);

    var sut = register.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_registerOfUserDataBaseReturnsFalse_shouldReturnFailResponse() {
    when(userDataBase.register(any())).thenReturn(false);
    var request = new Request(null, Map.of("name", "lol"));
    var expected = new Response("false", HttpStatusCode.OK);

    var sut = register.handleRequest(request);

    assertEquals(expected, sut);
  }
}
