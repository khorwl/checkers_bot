package server.api.handlers;

import core.ICheckersServer;
import server.api.http.HttpResponse;
import server.api.http.HttpStatusCode;
import server.api.http.HttpRequest;
import tools.QueryParser;

public class Delete extends CommandHandler {

  public Delete(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public HttpResponse handleRequest(HttpRequest httpRequest) {
    var name = httpRequest.getParameterOrNull("name");

    if (name == null) {
      return new HttpResponse("Invalid query", HttpStatusCode.BadRequest);
    }

    var user = server.userDataBase().getUserOrNull(name);

    if (user == null || server.sessionServer().hasSessionWithUser(user)) {
      return new HttpResponse("false", HttpStatusCode.OK);
    }

    return new HttpResponse(Boolean.toString(server.userDataBase().delete(name)), HttpStatusCode.OK);
  }
}
