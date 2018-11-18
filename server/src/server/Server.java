package server;

import com.sun.net.httpserver.HttpServer;
import core.ICheckersServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import server.api.handlers.Handler;
import server.api.handlers.HandlersLoader;
import server.api.handlers.delete.Delete;
import server.api.handlers.end_session.EndSession;
import server.api.handlers.enqueue.Enqueue;
import server.api.handlers.get_session.GetSession;
import server.api.handlers.have_session.HaveSession;
import server.api.handlers.home.Home;
import server.api.handlers.register.Register;
import tools.QueryParser;

public class Server {
  private final QueryParser queryParser;
  private final ICheckersServer server;

  public Server(ICheckersServer server) {
    this.server = server;

    queryParser = new QueryParser();
  }

  public void run(InetSocketAddress address) throws IOException, NoSuchMethodException {
    System.out.println(
        String.format("Starting server at %s:%s", address.getHostString(), address.getPort()));
    System.out.println("Use Ctrl+C to shutdown");

    HttpServer httpServer = HttpServer.create(address, 0);

    var handlers = HandlersLoader.createHandlers(queryParser, server);

    for (var handler : handlers) {
      httpServer.createContext("/" + handler.getName(), handler);
    }

    httpServer.setExecutor(null);

    httpServer.start();
  }
}