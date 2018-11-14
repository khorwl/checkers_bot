package server.handlers;

import core.ICheckersServer;
import server.Request;
import server.Response;
import tools.QueryParser;

public class MakeTurn extends CommandHandler {

  public MakeTurn(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response handleRequest(Request request) {
    return null;
  }
}
