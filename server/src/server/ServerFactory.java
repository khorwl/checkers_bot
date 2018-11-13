package server;

import core.CheckersServer;
import core.sessions.PlayerQueue;
import core.sessions.SessionServer;
import core.sessions.UserDataBase;

public class ServerFactory implements IServerFactory {

  @Override
  public Server create() {
    var userDataBase = new UserDataBase();
    var sessionServer = new SessionServer(null);
    var playerQueue = new PlayerQueue();
    var checkersServer = new CheckersServer(userDataBase, sessionServer, playerQueue);

    return new Server(checkersServer);
  }
}
