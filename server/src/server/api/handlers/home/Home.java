package server.api.handlers.home;

import core.ICheckersServer;
import server.api.handlers.CommandHandler;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.QueryParser;

public class Home extends CommandHandler<String> {

  public Home(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<String> handleRequest(HttpRequest httpRequest) {
    return Response.createSuccess("Welcome to checkers server");
  }
}
