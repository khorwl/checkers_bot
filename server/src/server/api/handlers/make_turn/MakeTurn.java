package server.api.handlers.make_turn;

import core.ICheckersServer;
import core.checkers.primitives.TurnStatus;
import core.checkers.primitives.Vector;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.QueryParser;

public class MakeTurn extends Handler<TurnStatus> {

  public MakeTurn(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<TurnStatus> handleRequest(HttpRequest httpRequest) {
    var name = httpRequest.getParameterOrNull("name");
    var from = httpRequest.getParameterOrNull("from");
    var to = httpRequest.getParameterOrNull("to");

    if (name == null || from == null || to == null) {
      return Response.createInvalidRequest("Invalid query", null);
    }

    var vectorFrom = Vector.tryParse(from);
    var vectorTo = Vector.tryParse(to);

    if (vectorFrom == null || vectorTo == null) {
      return Response.createInvalidRequest("Invalid vector format", null);
    }

    var user = server.userDataBase().getUserOrNull(name);

    if (user == null) {
      return Response.createSuccess(String.format("No such user: %s", name), null);
    }

    var session = server.sessionServer().getSessionWithUserOrNull(user);

    if (session == null) {
      return Response.createSuccess("No session with user " + name, null);
    }

    return null;
  }

  @Override
  public String getName() {
    return null;
  }
}
