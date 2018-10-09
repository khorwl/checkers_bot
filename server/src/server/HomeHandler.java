package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class HomeHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    var response = "Hello, I am chat bot";
    httpExchange.sendResponseHeaders(200, response.getBytes().length);

    var os = httpExchange.getResponseBody();

    os.write(response.getBytes());

    os.close();
  }
}
