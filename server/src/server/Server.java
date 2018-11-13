package server;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
  public Server() {
  }

  public void run(InetSocketAddress address) throws IOException {
    System.out.println(
        String.format("Starting server at %s:%s", address.getHostString(), address.getPort()));
    System.out.println("Use Ctrl+C to shutdown");

    HttpServer httpServer = HttpServer.create(address, 0);

    //add handlers here...
    httpServer.setExecutor(null);

    httpServer.start();
  }
}