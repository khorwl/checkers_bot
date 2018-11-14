package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpStatusCode;
import server.Request;
import server.Response;
import server.handlers.Home;

public class HomeUnitTests extends HandlerTestCase {
  private Home home;

  @BeforeEach
  private void init() {
    home = new Home(queryParser, server);
  }

  @Test
  public void handleRequest_shouldReturnRightResponse() {
    var request = new Request("lol", Map.of());
    var expected = new Response("Checkers web service", HttpStatusCode.OK);

    var sut = home.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_shouldNotCallServer() {
    home.handleRequest(new Request("lol", Map.of("name", "user")));

    verify(server, never()).playerQueue();
    verify(server, never()).userDataBase();
    verify(server, never()).sessionServer();
  }
}
