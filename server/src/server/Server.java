package server;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

  public void run(InetSocketAddress addres) throws IOException {
    System.out.println(
        String.format("Starting server at %s:%s", addres.getHostString(), addres.getPort()));
    System.out.println("Use Ctrl+C to shutdown");

    HttpServer httpServer = HttpServer.create(addres, 0);

    //add handlers here
    httpServer.setExecutor(null);

    httpServer.start();
  }
}