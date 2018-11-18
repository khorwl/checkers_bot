package server.api.handlers.have_session;

import core.checkers.ICheckersServer;
import core.userdb.UserDataBaseException;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import tools.QueryParser;

public class HaveSession extends Handler<Boolean> {

  public HaveSession(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<Boolean> handleRequest(HttpRequest httpRequest)
      throws NoThatParameterException, UserDataBaseException {
    var name = httpRequest.getParameter("name");
    var user = server.userDataBase().getUser(name);

    return Response.createSuccess(server.sessionServer().hasSessionWithUser(user));
  }

  @Override
  public String getName() {
    return "have_session";
  }
}
