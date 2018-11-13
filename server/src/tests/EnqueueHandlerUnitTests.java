package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.ICheckersServer;
import java.security.KeyException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.EnqueueHandler;
import server.HttpStatusCode;
import server.Request;
import server.Response;

public class EnqueueHandlerUnitTests {

  private ICheckersServer server;
  private EnqueueHandler handler;

  @BeforeEach
  public void init() {
    server = mock(ICheckersServer.class);
    handler = new EnqueueHandler(server);
  }

  @Test
  public void handleRequest_requestWithoutName_shouldReturnBadFormatResponse() {
    var request = new Request("lol", Map.of("key", "value"));
    var expected = new Response("Invalid query", HttpStatusCode.BadRequest);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withInvalidRequest_shouldNotCallCheckerServer() throws KeyException {
    handler.handleRequest(new Request("lol", Map.of()));

    verify(server, times(0)).enqueueUserToPlayerQueue(any());
  }

  @Test
  public void handleRequest_noSuchUser_shouldReturnBadRequest() throws KeyException {
    var request = new Request(null, Map.of("name", "user"));
    var expected = new Response("No such user: \"user\"", HttpStatusCode.NotFound);
    when(server.hasUser("user")).thenReturn(false);
    doThrow(new KeyException("No such user: \"user\"")).when(server).enqueueUserToPlayerQueue("user");

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handle_Request_withValidUserName_shouldReturnSuccessResponse() {
    var request = new Request(null, Map.of("name", "user"));
    var expected = new Response("successfully", HttpStatusCode.OK);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }
}
