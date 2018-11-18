package server.api.handlers.delete;

import core.ICheckersServer;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.QueryParser;

public class Delete extends Handler<Boolean> {

  public Delete(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<Boolean> handleRequest(HttpRequest httpRequest) {
    var name = httpRequest.getParameterOrNull("name");

    if (name == null)
      return Response.createInvalidRequest();

    var user = server.userDataBase().getUserOrNull(name);

    if (user == null)
      return Response.createSuccess("No such user", false);

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
