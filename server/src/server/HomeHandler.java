package server;

import com.sun.net.httpserver.HttpExchange;
import infra.ICheckersServer;
import java.io.IOException;

public class HomeHandler extends CommandHandler {

  public HomeHandler(ICheckersServer checkersServer) {
    super(checkersServer);
  }

  @Override
  public Response handleRequest(Request request) {
    return new Response("This is service for play 3d checkers", HttpStatusCode.OK);
  }
}
