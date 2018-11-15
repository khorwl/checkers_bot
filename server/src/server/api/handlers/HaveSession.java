package server.api.handlers;

import core.ICheckersServer;
import core.userdb.UserDataBaseException;
import server.api.http.HttpResponse;
import server.api.http.HttpStatusCode;
import server.api.http.HttpRequest;
import tools.QueryParser;

public class HaveSession extends CommandHandler {

  public HaveSession(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public HttpResponse handleRequest(HttpRequest httpRequest) {
    var name = httpRequest.getParameterOrNull("name");

    if (name == null) {
      return new HttpResponse("Invalid query", HttpStatusCode.BadRequest);
    }

    try {
      var user = server.userDataBase().getUser(name);

      return new HttpResponse(Boolean.toString(server.sessionServer().hasSessionWithUser(user)), HttpStatusCode.OK);
    } catch (UserDataBaseException e) {
      return new HttpResponse(String.format("No such user: \"%s\"", name), HttpStatusCode.BadRequest);
    }
  }
}
