package server;

import com.sun.net.httpserver.HttpExchange;
import infra.ICheckersServer;
import java.io.IOException;

public class RegisterHandler extends CommandHandler {

  public RegisterHandler(ICheckersServer checkersServer) {
    super(checkersServer);
  }

  @Override
  public Response handleRequest(Request request) {
    return new Response("register", HttpStatusCode.OK);
  }
}
