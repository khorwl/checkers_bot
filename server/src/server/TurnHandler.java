package server;

import com.sun.net.httpserver.HttpExchange;
import infra.ICheckersServer;
import java.io.IOException;

public class TurnHandler extends CommandHandler {

  public TurnHandler(ICheckersServer checkersServer) {
    super(checkersServer);
  }

  @Override
  public Response handleRequest(Request request) {
    return new Response("turn", HttpStatusCode.OK);
  }
}
