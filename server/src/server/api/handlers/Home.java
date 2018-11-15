package server.api.handlers;

import core.ICheckersServer;
import server.api.http.HttpResponse;
import server.api.http.HttpStatusCode;
import server.api.http.HttpRequest;
import tools.QueryParser;

public class Home extends CommandHandler {

  public Home(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public HttpResponse handleRequest(HttpRequest httpRequest) {
    return new HttpResponse("Checkers web service", HttpStatusCode.OK);
  }
}
