package server;

import infra.ICheckersServer;

public class RegisterHandler extends CommandHandler {

  public RegisterHandler(ICheckersServer server) {
    super(server);
  }

  @Override
  public Response handleRequest(Request request) {
    var name = request.getParameterOrNull("name");

    if (name == null)
      return new Response("Invalid query", HttpStatusCode.BadRequest);

    if (!server.registerUser(name))
      return new Response(String.format("Cant register user \"%s\"", name), HttpStatusCode.Conflict);

    return new Response(String.format("Successfully register user \"%s\"", name), HttpStatusCode.OK);
  }
}
