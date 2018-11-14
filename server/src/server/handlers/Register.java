package server.handlers;

import core.ICheckersServer;
import server.HttpStatusCode;
import server.Request;
import server.Response;
import tools.QueryParser;

public class Register extends CommandHandler {

  public Register(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response handleRequest(Request request) {
    var name = request.getParameterOrNull("name");

    if (name == null)
      return new Response("Invalid query", HttpStatusCode.BadRequest);

    return new Response(Boolean.toString(server.userDataBase().register(name)), HttpStatusCode.OK);
  }
}
