package server;

import com.sun.net.httpserver.HttpServer;
import core.ICheckersServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import server.handlers.Home;
import tools.QueryParser;

public class Server {
  private final QueryParser queryParser;
  private final ICheckersServer server;

  public Server(ICheckersServer server) {
    this.server = server;

    queryParser = new QueryParser();
  }

  public void run(InetSocketAddress address) throws IOException {
    System.out.println(
        String.format("Starting server at %s:%s", address.getHostString(), address.getPort()));
    System.out.println("Use Ctrl+C to shutdown");

    HttpServer httpServer = HttpServer.create(address, 0);

    httpServer.createContext("/", new Home(queryParser, server));
    httpServer.setExecutor(null);

    httpServer.start();
  }
}