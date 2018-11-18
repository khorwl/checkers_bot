package server.api.handlers.enqueue;

import core.checkers.ICheckersServer;
import core.sessions.SessionServerException;
import core.userdb.UserDataBaseException;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import tools.QueryParser;

public class Enqueue extends Handler<Boolean> {

  public Enqueue(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<Boolean> handleRequest(HttpRequest httpRequest)
      throws UserDataBaseException, NoThatParameterException, SessionServerException {
    var response = getResponse(httpRequest);

    startSessionIfPossible();

    return response;
  }

  @Override
  public String getName() {
    return "enqueue";
  }

  private Response<Boolean> getResponse(HttpRequest httpRequest)
      throws NoThatParameterException, UserDataBaseException {
    var name = httpRequest.getParameter("name");
    var user = server.userDataBase().getUser(name);

    if (server.sessionServer().hasSessionWithUser(user)) {
      return Response.createSuccess(String.format("User %s already have session", name), false);
    }

    if (server.playerQueue().enqueue(user)) {
      return Response.createSuccess(String.format("Successfully enqueued user %s", name), true);
    }

    return Response.createSuccess(String.format("Cant enqueue user %s", name), false);
  }

  private void startSessionIfPossible() throws SessionServerException {
    var pair = server.playerQueue().dequeuePairElseNull();

    if (pair == null) {
      return;
    }

    server.sessionServer().createSession(pair.first(), pair.second());
  }
}
