package core.sessions;

import core.checkers.IGame;
import core.checkers.IGameFactory;
import core.checkers.players.EasyAIPlayer;
import core.userdb.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SessionServer implements ISessionServer {

  private final IGameFactory gameFactory;
  private final Map<String, Session> idToSession;
  private final Map<User, String> userToCurrentSessionId;

  public SessionServer(IGameFactory gameFactory) {
    this.gameFactory = gameFactory;
    idToSession = new HashMap<>();
    userToCurrentSessionId = new HashMap<>();
  }

  @Override
  public Session createSession(User whiteUser, User blackUser) throws SessionServerException {
    if (hasSessionWithUser(whiteUser)) {
      throwAlreadyHaveSession(whiteUser);
    }

    if (hasSessionWithUser(blackUser)) {
      throwAlreadyHaveSession(whiteUser);
    }

    var game = gameFactory.createGame(whiteUser, blackUser);
    var session = createSessionForGame(game);

    userToCurrentSessionId.put(whiteUser, session.getId());
    userToCurrentSessionId.put(blackUser, session.getId());

    return session;
  }

  @Override
  public Session createAISessionForWhite(User user) throws SessionServerException {
    if (hasSessionWithUser(user)) {
      throwAlreadyHaveSession(user);
    }

    var game = gameFactory.createGame(user, new EasyAIPlayer());

    return createSessionForGame(game);
  }

  @Override
  public Session createAISessionForBlack(User user) throws SessionServerException {
    if (hasSessionWithUser(user))
      throwAlreadyHaveSession(user);

    var game = gameFactory.createGame(new EasyAIPlayer(), user);

    return createSessionForGame(game);
  }

  private Session createSessionForGame(IGame game) {
    var id = UUID.randomUUID().toString();
    var session = new Session(id, game);

    idToSession.put(id, session);

    return session;
  }

  @Override
  public List<Session> getSessions() {
    return new ArrayList<>(idToSession.values());
  }

  @Override
  public Session getSession(String sessionId) throws SessionServerException {
    var session = getSessionElseNull(sessionId);

    if (session == null) {
      throwNotThatSessionException(sessionId);
    }

    return session;
  }

  @Override
  public Session getSessionElseNull(String sessionId) {
    return idToSession.get(sessionId);
  }

  @Override
  public Session getSessionWithUser(User user) throws SessionServerException {
    var session = getSessionWithUserElseNull(user);

    if (session == null)
      throw new SessionServerException(String.format("No session with user: %s", user.getName()));

    return session;
  }

  @Override
  public Session getSessionWithUserElseNull(User user) {
    var id = userToCurrentSessionId.get(user);

    if (id == null)
      return null;

    return idToSession.get(id);
  }

  @Override
  public boolean hasSessionWithUser(User user) {
    return userToCurrentSessionId.containsKey(user);
  }

  @Override
  public void endSession(Session session) throws SessionServerException {
    endSession(session.getId());
  }

  @Override
  public void endSession(String sessionId) throws SessionServerException {
    var session = idToSession.get(sessionId);

    if (session == null) {
      throwNotThatSessionException(sessionId);
    }

    var whitePlayer = session.getGame().getWhitePlayer();
    var blackPlayer = session.getGame().getBlackPlayer();

    if (whitePlayer instanceof User)
      userToCurrentSessionId.remove(whitePlayer);

    if (blackPlayer instanceof User)
      userToCurrentSessionId.remove(blackPlayer);

    idToSession.remove(sessionId);
  }

  @Override
  public boolean hasSession(String sessionId) {
    return idToSession.containsKey(sessionId);
  }

  private void throwNotThatSessionException(String id) throws SessionServerException {
    throw new SessionServerException(String.format("No that session with id: %s", id));
  }

  private void throwAlreadyHaveSession(User user) throws SessionServerException {
    throw new SessionServerException(String.format("User %s already have session", user.getName()));
  }
}
