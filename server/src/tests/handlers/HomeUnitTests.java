package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.handlers.home.Home;
import server.api.http.HttpRequest;
import server.api.response.Response;

public class HomeUnitTests extends HandlerTestCase {
  private Home home;

  @BeforeEach
  private void init() {
    home = new Home(queryParser, server);
  }

  @Test
  public void handleRequest_shouldReturnRightResponse() {
    var request = new HttpRequest("lol", Map.of());
    var expected = Response.createSuccess("Welcome to checkers server");

    var sut = home.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_shouldNotCallServer() {
    home.handleRequest(new HttpRequest("lol", Map.of("name", "user")));

    verify(server, never()).playerQueue();
    verify(server, never()).userDataBase();
    verify(server, never()).sessionServer();
  }
}
