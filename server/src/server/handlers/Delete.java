package server.handlers;

import core.ICheckersServer;
import server.HttpStatusCode;
import server.Request;
import server.Response;
import tools.QueryParser;

public class Delete extends CommandHandler {

  public Delete(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response handleRequest(Request request) {
    var name = request.getParameterOrNull("name");

    if (name == null) {
      return new Response("Invalid query", HttpStatusCode.BadRequest);
    }

    var user = server.userDataBase().getUserOrNull(name);

    if (user == null || server.sessionServer().hasSessionWithUser(user)) {
      return new Response("false", HttpStatusCode.OK);
    }

    return new Response(Boolean.toString(server.userDataBase().delete(name)), HttpStatusCode.OK);
  }
}
