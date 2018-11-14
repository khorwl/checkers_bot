package server;

import core.CheckersServer;
import core.queue.PlayerQueue;
import core.sessions.SessionServer;
import core.userdb.UserDataBase;

public class ServerFactory implements IServerFactory {

  @Override
  public Server create() {
    var userDataBase = new UserDataBase();
    var sessionServer = new SessionServer(null);
    var playerQueue = new PlayerQueue();
    var server = new CheckersServer(userDataBase, playerQueue, sessionServer);

    return new Server(server);
  }
}
