package core.sessions;

import core.checkers.IGame;
import core.checkers.IGameFactory;
import core.checkers.players.EasyAIPlayer;
import core.userdb.User;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SessionServer implements ISessionServer {
  private final IGameFactory gameFactory;
  private final Map<String, Session> idToSession;

  public SessionServer(IGameFactory gameFactory) {
    this.gameFactory = gameFactory;
    idToSession = new HashMap<>();
  }

  @Override
  public Session createSession(User whiteUser, User blackUser) {
    var game = gameFactory.createGame(whiteUser, blackUser);

    return createSessionForGame(game);
  }

  @Override
  public Session createAISessionForWhite(User user) {
    var game = gameFactory.createGame(user, new EasyAIPlayer());

    return createSessionForGame(game);
  }

  @Override
  public Session createAISessionForBlack(User user) {
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
  public Session getSession(String sessionId) throws KeyException {
    if (!idToSession.containsKey(sessionId))
      throwNotThatSessionException(sessionId);

    return idToSession.get(sessionId);
  }

  @Override
  public void endSession(Session session) throws KeyException {
    endSession(session.getId());
  }

  @Override
  public void endSession(String sessionId) throws KeyException {
    if (!idToSession.containsKey(sessionId))
      throwNotThatSessionException(sessionId);

    idToSession.remove(sessionId);
  }

  @Override
  public boolean hasSession(String sessionId) {
    return idToSession.containsKey(sessionId);
  }

  private void throwNotThatSessionException(String id) throws KeyException {
    throw new KeyException(String.format("No that session with id: %s", id));
  }
}
