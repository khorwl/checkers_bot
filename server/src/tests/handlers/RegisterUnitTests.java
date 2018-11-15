package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.handlers.register.Register;
import server.api.http.HttpRequest;
import server.api.response.Response;

public class RegisterUnitTests extends HandlerTestCase {
  private Register register;

  @BeforeEach
  private void init() {
    register = new Register(queryParser, server);
  }

  @Test
  public void handleRequest_noNameParameter_shouldReturnBadRequest() {
    var request = new HttpRequest(null, Map.of("namE", "u$ser"));
    var expected = Response.createInvalidRequest("Invalid query", false);

    var sut = register.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_registerOfUserDataBaseReturnsTrue_shouldReturnSuccessResponse() {
    when(userDataBase.register("username")).thenReturn(true);
    var request = new HttpRequest(null, Map.of("name", "username"));
    var expected = Response.createSuccess("Successfully register user username", true);

    var sut = register.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_registerOfUserDataBaseReturnsFalse_shouldReturnFailResponse() {
    when(userDataBase.register(any())).thenReturn(false);
    var request = new HttpRequest(null, Map.of("name", "lol"));
    var expected = Response.createSuccess("Cant register user lol", false);

    var sut = register.handleRequest(request);

    assertEquals(expected, sut);
  }
}
