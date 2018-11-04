package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sun.net.httpserver.HttpExchange;
import infra.ICheckersServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.RegisterHandler;


public class RegisterHandlerUnitTests {
  private RegisterHandler handler;
  private ICheckersServer server;
  private HttpExchange exchange;

  @BeforeEach
  public void init() {
    server = mock(ICheckersServer.class);
    handler = new RegisterHandler(server);
    exchange = mock(HttpExchange.class);
  }

  @Test
  public void handle_withInvalidQuery_shouldReturn() {


  }

}
