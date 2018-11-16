package server.api.handlers.have_session;

import core.ICheckersServer;
import core.userdb.UserDataBaseException;
import server.api.handlers.CommandHandler;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.QueryParser;

public class HaveSession extends CommandHandler<Boolean> {

  public HaveSession(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<Boolean> handleRequest(HttpRequest httpRequest) {
    var name = httpRequest.getParameterOrNull("name");

    if (name == null)
      return Response.createInvalidRequest("Invalid query", false);

    try {
      var user = server.userDataBase().getUser(name);

      return Response.createSuccess(server.sessionServer().hasSessionWithUser(user));
    } catch (UserDataBaseException e) {
      return Response.createSuccess(String.format("No such user: \"%s\"", name), false);
    }
  }
}
