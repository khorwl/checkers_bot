package server;

import com.sun.net.httpserver.HttpExchange;
import infra.ICheckersServer;
import java.io.IOException;

public class DeleteHandler extends CommandHandler {

  public DeleteHandler(ICheckersServer checkersServer) {
    super(checkersServer);
  }

  @Override
  public Response handleRequest(Request request) {
    return new Response("delete", HttpStatusCode.OK);
  }
}
