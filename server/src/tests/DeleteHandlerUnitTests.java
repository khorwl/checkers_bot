package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.ICheckersServer;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.DeleteHandler;
import server.HttpStatusCode;
import server.Request;
import server.Response;


public class DeleteHandlerUnitTests {

  private DeleteHandler handler;
  private ICheckersServer server;

  @BeforeEach
  public void init() {
    server = mock(ICheckersServer.class);
    handler = new DeleteHandler(server);
  }

  @Test
  public void handleRequest_withInvalidQuery_shouldReturnResponseWithBadRequestCode() {
    var query = Map.of("Name", "value", "__name", "value", "lol", "mde");
    var request = new Request("", query);
    var expected = new Response("Invalid query", HttpStatusCode.BadRequest);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withNoExistingUser_shouldReturnResponseWithConflictCode() {
    var name = "username";
    var query = Map.of("name", name);
    when(server.deleteUser(name)).thenReturn(false);
    var request = new Request("", query);
    var expected = new Response(
        String.format("Cant delete user \"%s\"", name),
        HttpStatusCode.Conflict);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_shouldCallDeleteOfServerOnce() {
    var name = "username";
    var query = Map.of("name", name);
    var request = new Request("", query);

    handler.handleRequest(request);

    verify(server, times(1)).deleteUser(name);
  }

  @Test
  public void handleRequest_serversReturnsTrue_shouldReturnRightResponse() {
    var name = "username";
    var query = Map.of("name", name);
    when(server.deleteUser(name)).thenReturn(true);
    var request = new Request("", query);
    var expected = new Response(
        String.format("Successfully delete user \"%s\"", name),
        HttpStatusCode.OK);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }
}
