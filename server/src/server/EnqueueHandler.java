package server;

import com.sun.net.httpserver.HttpExchange;
import infra.ICheckersServer;
import java.io.IOException;
import java.security.KeyException;

public class EnqueueHandler extends CommandHandler {

  public EnqueueHandler(ICheckersServer server) {
    super(server);
  }

  @Override
  public Response handleRequest(Request request) {
    var name = request.getParameterOrNull("name");

    if (name == null)
      return new Response("Invalid query", HttpStatusCode.BadRequest);

    try {
      server.enqueueUserToPlayerQueue(name);

      return new Response("successfully", HttpStatusCode.OK);
    } catch (KeyException e) {
      return new Response(String.format("No such user: \"%s\"", name), HttpStatusCode.NotFound);
    }
  }
}
