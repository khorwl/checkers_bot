package server.api.handlers.end_session;

import core.ICheckersServer;
import core.sessions.SessionServerException;
import core.userdb.UserDataBaseException;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import tools.QueryParser;

public class EndSession extends Handler<Boolean> {

  public EndSession(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<Boolean> handleRequest(HttpRequest httpRequest)
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var name = httpRequest.getParameter("name");
    var user = server.userDataBase().getUser(name);
    var session = server.sessionServer().getSessionWithUser(user);

    server.sessionServer().endSession(session);

    return Response.createSuccess(String.format("Successfully ended session %s", session.getId()), true);
  }

  @Override
  public String getName() {
    return "end_session";
  }
}
