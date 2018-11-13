package server;

import core.ICheckersServer;

public class DeleteHandler extends CommandHandler {

  public DeleteHandler(ICheckersServer server) {
    super(server);
  }

  @Override
  public Response handleRequest(Request request) {
    var name = request.getParameterOrNull("name");

    if (name == null)
      return new Response("Invalid query", HttpStatusCode.BadRequest);

    if (!server.deleteUser(name))
      return new Response(String.format("Cant delete user \"%s\"", name), HttpStatusCode.Conflict);

    return new Response(String.format("Successfully delete user \"%s\"", name), HttpStatusCode.OK);
  }
}
