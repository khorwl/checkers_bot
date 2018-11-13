package core;

import core.checkers.primitives.TurnStatus;
import core.checkers.primitives.Vector;
import core.sessions.IPlayerQueue;
import core.sessions.ISessionServer;
import core.sessions.IUserDataBase;
import core.sessions.Session;
import core.sessions.User;
import java.security.KeyException;
import java.util.Set;

public class CheckersServer implements ICheckersServer {

  private final IUserDataBase userDataBase;
  private final ISessionServer sessionServer;
  private final IPlayerQueue playerQueue;

  public CheckersServer(
      IUserDataBase userDataBase,
      ISessionServer sessionServer,
      IPlayerQueue playerQueue) {
    this.userDataBase = userDataBase;
    this.sessionServer = sessionServer;
    this.playerQueue = playerQueue;
  }

  @Override
  public boolean registerUser(String name) {
    return userDataBase.register(name);
  }

  @Override
  public boolean deleteUser(String name) {
    return userDataBase.delete(name);
  }

  @Override
  public Set<User> getUsers() {
    return userDataBase.getUsers();
  }

  @Override
  public boolean hasUser(String name) {
    return userDataBase.hasUser(name);
  }

  @Override
  public void enqueueUserToPlayerQueue(String name) throws KeyException {
    playerQueue.enqueue(userDataBase.getUser(name));
  }

  @Override
  public boolean hasPairToStartSession() {
    return playerQueue.hasPair();
  }

  @Override
  public Session startSession() {
    var pair = playerQueue.dequeuePair();

    return sessionServer.createSession(pair.first(), pair.second());
  }

  @Override
  public boolean makeTurn(String sessionId, Vector from, Vector to) throws KeyException {
    var session = sessionServer.getSession(sessionId);
    var state = session.getGame().makeNextTurn(from, to);

    return state == TurnStatus.SUCCESS;
  }

  @Override
  public Session getSession(String sessionId) throws KeyException {
    return sessionServer.getSession(sessionId);
  }
}
