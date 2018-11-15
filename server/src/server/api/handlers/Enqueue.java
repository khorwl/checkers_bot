package server.api.handlers;

import core.ICheckersServer;
import server.api.http.HttpResponse;
import server.api.http.HttpRequest;
import tools.QueryParser;

public class Enqueue extends CommandHandler {

  public Enqueue(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public HttpResponse handleRequest(HttpRequest httpRequest) {
    return null;
  }
}
