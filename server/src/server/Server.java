package server;

import com.sun.net.httpserver.HttpServer;
import core.queue.IPlayerQueue;
import core.sessions.ISessionServer;
import core.userdb.IUserDataBase;
import java.io.IOException;
import java.net.InetSocketAddress;
import server.handlers.Home;
import tools.QueryParser;

public class Server {
  private final QueryParser queryParser;
  private final IUserDataBase userDataBase;
  private final ISessionServer sessionServer;
  private final IPlayerQueue playerQueue;

  public Server(
      IUserDataBase userDataBase,
      ISessionServer sessionServer,
      IPlayerQueue playerQueue) {
    this.userDataBase = userDataBase;
    this.sessionServer = sessionServer;
    this.playerQueue = playerQueue;
    queryParser = new QueryParser();
  }

  public void run(InetSocketAddress address) throws IOException {
    System.out.println(
        String.format("Starting server at %s:%s", address.getHostString(), address.getPort()));
    System.out.println("Use Ctrl+C to shutdown");

    HttpServer httpServer = HttpServer.create(address, 0);

    httpServer.createContext("/", new Home(queryParser, userDataBase, sessionServer, playerQueue));
    httpServer.setExecutor(null);

    httpServer.start();
  }
}