package server.api.handlers.delete;

import core.ICheckersServer;
import core.userdb.UserDataBaseException;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import tools.QueryParser;

public class Delete extends Handler<Boolean> {

  public Delete(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<Boolean> handleRequest(HttpRequest httpRequest)
      throws NoThatParameterException, UserDataBaseException {
    var name = httpRequest.getParameter("name");
    var user = server.userDataBase().getUser(name);

    if (server.sessionServer().hasSessionWithUser(user))
      return Response.createSuccess("User have open session", false);

    if (!server.userDataBase().delete(name))
      return Response.createSuccess(String.format("Cant delete user %s", name), false);

    return Response.createSuccess(String.format("Successfully delete user %s", name), true);
  }

  @Override
  public String getName() {
    return "delete";
  }
}
