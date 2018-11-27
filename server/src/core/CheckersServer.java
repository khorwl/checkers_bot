package core;

import com.google.inject.Inject;
import core.queue.IPlayerQueue;
import core.sessions.ISessionServer;
import core.userdb.IUserDataBase;

public class CheckersServer implements ICheckersServer {

  private final IUserDataBase userDataBase;
  private final IPlayerQueue playerQueue;
  private final ISessionServer sessionServer;

  @Inject
  public CheckersServer(
      IUserDataBase userDataBase,
      IPlayerQueue playerQueue,
      ISessionServer sessionServer) {
    this.userDataBase = userDataBase;
    this.playerQueue = playerQueue;
    this.sessionServer = sessionServer;
  }

  @Override
  public IUserDataBase userDataBase() {
    return userDataBase;
  }

  @Override
  public IPlayerQueue playerQueue() {
    return playerQueue;
  }

  @Override
  public ISessionServer sessionServer() {
    return sessionServer;
  }
}
