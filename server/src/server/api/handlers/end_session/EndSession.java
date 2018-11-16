package server.api.handlers.end_session;

import core.ICheckersServer;
import core.sessions.SessionServerException;
import server.api.handlers.CommandHandler;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.QueryParser;

public class EndSession extends CommandHandler<Boolean> {

  public EndSession(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<Boolean> handleRequest(HttpRequest httpRequest) {
    var name = httpRequest.getParameterOrNull("name");

    if (name == null) {
      return Response.createInvalidRequest("Invalid query", false);
    }

    var user = server.userDataBase().getUserOrNull(name);

    if (user == null) {
      return Response.createSuccess(String.format("No that user: %s", name), false);
    }

    var session = server.sessionServer().getSessionWithUserOrNull(user);

    if (session == null) {
      return Response.createSuccess("No any session with user", false);
    }

    //todo: make more beautiful
    try {
      server.sessionServer().endSession(session);

      return Response.createSuccess(String.format("Successfully ended session %s", session.getId()), true);
    } catch (SessionServerException e) {
      //probably impossible scenario, should be fixed
      return Response.createSuccess(String.format("Cant kill session %s", session.getId()), false);
    }
  }
}
