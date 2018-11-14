package server.handlers;

import core.ICheckersServer;
import server.HttpStatusCode;
import server.Request;
import server.Response;
import tools.QueryParser;

public class Home extends CommandHandler {

  public Home(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response handleRequest(Request request) {
    return new Response("Checkers web service", HttpStatusCode.OK);
  }
}
