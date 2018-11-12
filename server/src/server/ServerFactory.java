package server;

import infra.CheckersServer;
import infra.sessions.PlayerQueue;
import infra.sessions.SessionServer;
import infra.sessions.UserDataBase;

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
