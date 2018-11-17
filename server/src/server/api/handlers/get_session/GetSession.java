package server.api.handlers.get_session;

import core.ICheckersServer;
import core.checkers.players.AIPlayer;
import core.checkers.players.IPlayer;
import core.sessions.Session;
import core.userdb.User;
import java.util.ArrayList;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.QueryParser;

public class GetSession extends Handler<ClientSessionInfo> {

  public GetSession(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<ClientSessionInfo> handleRequest(HttpRequest httpRequest) {
    var name = httpRequest.getParameterOrNull("name");

    if (name == null) {
      return Response.createInvalidRequest("Invalid query", null);
    }

    var user = server.userDataBase().getUserOrNull(name);

    if (user == null) {
      return Response.createSuccess(String.format("No such user: %s", name), null);
    }

    var session = server.sessionServer().getSessionWithUserOrNull(user);

    if (session == null) {
      return Response.createSuccess(String.format("No session with user: %s", name), null);
    }

    return Response.createSuccess("Successfully find session", transformSession(session));
  }

  @Override
  public String getName() {
    return "get_session";
  }

  private ClientSessionInfo transformSession(Session session) {
    var checkers = new ArrayList<>(session.getGame().getCheckers());
    var id = session.getId();
    var whiteName = getNameOf(session.getGame().getWhitePlayer());
    var blackName = getNameOf(session.getGame().getBlackPlayer());

    return new ClientSessionInfo(checkers, id, whiteName, blackName);
  }

  private String getNameOf(IPlayer player) {
    if (player instanceof User) {
      return ((User) player).getName();
    }

    if (player instanceof AIPlayer) {
      return "AI player";
    }

    return "Unknown player";
  }
}
