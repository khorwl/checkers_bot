package server.api.handlers;

import core.ICheckersServer;
import server.api.http.HttpResponse;
import server.api.http.HttpStatusCode;
import server.api.http.HttpRequest;
import tools.QueryParser;

public class Register extends CommandHandler {

  public Register(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public HttpResponse handleRequest(HttpRequest httpRequest) {
    var name = httpRequest.getParameterOrNull("name");

    if (name == null)
      return new HttpResponse("Invalid query", HttpStatusCode.BadRequest);

    return new HttpResponse(Boolean.toString(server.userDataBase().register(name)), HttpStatusCode.OK);
  }
}
