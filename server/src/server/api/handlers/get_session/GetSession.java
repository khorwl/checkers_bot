package server.api.handlers.get_session;

import core.ICheckersServer;
import core.checkers.players.AIPlayer;
import core.checkers.players.IPlayer;
import core.sessions.Session;
import core.sessions.SessionServerException;
import core.userdb.User;
import core.userdb.UserDataBaseException;
import java.util.ArrayList;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import tools.QueryParser;

public class GetSession extends Handler<ClientSessionInfo> {

  public GetSession(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<ClientSessionInfo> handleRequest(HttpRequest httpRequest)
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var name = httpRequest.getParameter("name");
    var user = server.userDataBase().getUser(name);
    var session = server.sessionServer().getSessionWithUser(user);

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
    var state = session.getGame().getState();

    return new ClientSessionInfo(checkers, id, whiteName, blackName, state);
  }

  private String getNameOf(IPlayer player) {
    if (player instanceof User) {
      return ((User) player).getName();
    }

    if (player instanceof AIPlayer) {
      return "AI";
    }

    return "Unknown player";
  }
}
