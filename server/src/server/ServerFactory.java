package server;

import core.checkers.CheckersServer;
import core.checkers.GameFactory;
import core.queue.PlayerQueue;
import core.sessions.SessionServer;
import core.userdb.UserDataBase;

public class ServerFactory implements IServerFactory {

  @Override
  public Server create() {
    var userDataBase = new UserDataBase();
    var gameFactory = new GameFactory();
    var sessionServer = new SessionServer(gameFactory);
    var playerQueue = new PlayerQueue();
    var checkersServer = new CheckersServer(userDataBase, playerQueue, sessionServer);

    return new Server(checkersServer);
  }
}
