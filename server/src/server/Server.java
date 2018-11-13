package server;

import com.sun.net.httpserver.HttpServer;
import core.ICheckersServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
  private final ICheckersServer checkersServer;

  public Server(ICheckersServer checkersServer) {
    this.checkersServer = checkersServer;
  }

  public void run(InetSocketAddress address) throws IOException {
    System.out.println(
        String.format("Starting server at %s:%s", address.getHostString(), address.getPort()));
    System.out.println("Use Ctrl+C to shutdown");

    HttpServer httpServer = HttpServer.create(address, 0);

    httpServer.createContext("/", new HomeHandler(checkersServer));
    httpServer.createContext("/register", new RegisterHandler(checkersServer));
    httpServer.createContext("/delete", new DeleteHandler(checkersServer));
    httpServer.createContext("/turn", new TurnHandler(checkersServer));
    httpServer.createContext("/enqueue", new EnqueueHandler(checkersServer));
    httpServer.createContext("/get_session", new GetPlayingSessionHandler(checkersServer));
    httpServer.setExecutor(null);

    httpServer.start();
  }
}