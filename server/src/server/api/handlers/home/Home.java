package server.api.handlers.home;

import core.checkers.ICheckersServer;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.QueryParser;

public class Home extends Handler<String> {

  public Home(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<String> handleRequest(HttpRequest httpRequest) {
    return Response.createSuccess("Welcome to checkers server");
  }

  @Override
  public String getName() {
    return "";
  }
}
