package server.api.handlers.make_turn;

import core.ICheckersServer;
import core.checkers.primitives.Turn;
import core.checkers.primitives.TurnStatus;
import core.checkers.primitives.Vector;
import core.sessions.SessionServerException;
import core.userdb.UserDataBaseException;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import tools.QueryParser;

public class MakeTurn extends Handler<TurnStatus> {

  public MakeTurn(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<TurnStatus> handleRequest(HttpRequest httpRequest)
      throws UserDataBaseException, NoThatParameterException, SessionServerException {
    var name = httpRequest.getParameter("name");
    var from = httpRequest.getParameter("from");
    var to = httpRequest.getParameter("to");
    var vectorFrom = Vector.parseElseNull(from);
    var vectorTo = Vector.parseElseNull(to);

    if (vectorFrom == null || vectorTo == null) {
      return Response.createInvalidRequest("Invalid vector format", null);
    }

    var user = server.userDataBase().getUser(name);
    var session = server.sessionServer().getSessionWithUser(user);
    var turn = new Turn(vectorFrom, vectorTo);

    user.setNextTurn(turn);

    return Response.createSuccess(session.getGame().performNextTurn());
  }

  @Override
  public String getName() {
    return null;
  }
}
