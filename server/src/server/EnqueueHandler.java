package server;

import com.sun.net.httpserver.HttpExchange;
import infra.ICheckersServer;
import java.io.IOException;
import java.security.KeyException;

public class EnqueueHandler extends CommandHandler {

  public EnqueueHandler(ICheckersServer checkersServer) {
    super(checkersServer);
  }

  @Override
  public Response handleRequest(Request request) {
    return new Response("enqueue", HttpStatusCode.OK);
  }
}
