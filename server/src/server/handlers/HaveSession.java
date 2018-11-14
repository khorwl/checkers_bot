package server.handlers;

import core.ICheckersServer;
import core.userdb.UserDataBaseException;
import server.HttpStatusCode;
import server.Request;
import server.Response;
import tools.QueryParser;

public class HaveSession extends CommandHandler {

  public HaveSession(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response handleRequest(Request request) {
    var name = request.getParameterOrNull("name");

    if (name == null) {
      return new Response("Invalid query", HttpStatusCode.BadRequest);
    }

    try {
      var user = server.userDataBase().getUser(name);

      return new Response(Boolean.toString(server.sessionServer().hasSessionWithUser(user)), HttpStatusCode.OK);
    } catch (UserDataBaseException e) {
      return new Response(String.format("No such user: \"%s\"", name), HttpStatusCode.BadRequest);
    }
  }
}
