package server.api.handlers.enqueue;

import core.ICheckersServer;
import core.sessions.SessionServerException;
import server.api.handlers.CommandHandler;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.QueryParser;

public class Enqueue extends CommandHandler<Boolean> {

  public Enqueue(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<Boolean> handleRequest(HttpRequest httpRequest) {
    var response = getResponse(httpRequest);

    startSessionIfPossible();

    return response;
  }

  private Response<Boolean> getResponse(HttpRequest httpRequest) {
    var name = httpRequest.getParameterOrNull("name");
    if (name == null) {
      return Response.createInvalidRequest("Invalid query", false);
    }

    var user = server.userDataBase().getUserOrNull(name);

    if (user == null) {
      return Response.createSuccess(String.format("No such user: %s", name), false);
    }

    if (server.sessionServer().hasSessionWithUser(user)) {
      return Response.createSuccess(String.format("User %s already have session", name), false);
    }

    if (server.playerQueue().enqueue(user)) {
      return Response.createSuccess(String.format("Successfully enqueued user %s", name), true);
    }

    return Response.createSuccess(String.format("Cant enqueue user %s", name), false);
  }

  private void startSessionIfPossible() {
    var pair = server.playerQueue().dequeuePairOrNull();

    if (pair == null) {
      return;
    }

    //TODO: make ISessionServer.createSession more peacefully, and delete this shit
    try {
      server.sessionServer().createSession(pair.first(), pair.second());
    } catch (SessionServerException e) {
      //impossible situation
    }
  }
}
