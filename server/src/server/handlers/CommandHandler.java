package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import core.ICheckersServer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import server.Request;
import server.Response;
import tools.QueryParser;

public abstract class CommandHandler implements HttpHandler {

  protected final QueryParser queryParser;
  protected final ICheckersServer server;

  public CommandHandler(QueryParser queryParser, ICheckersServer server) {
    this.queryParser = queryParser;
    this.server = server;
  }

  private void sendResponseAndClose(HttpExchange httpExchange, String body, int statusCode)
      throws IOException {
    var bytes = body != null ? body.getBytes(StandardCharsets.UTF_8) : new byte[0];

    httpExchange.getResponseHeaders().add("encoding", "utf-8");
    httpExchange.sendResponseHeaders(statusCode, bytes.length);
    httpExchange.getResponseBody().write(bytes);
    httpExchange.close();
  }

  private void sendResponseAndClose(HttpExchange httpExchange, Response response)
      throws IOException {
    sendResponseAndClose(httpExchange, response.getBody(), response.getStatusCode());
  }

  public final void handle(HttpExchange exchange) throws IOException {
    var request = buildRequest(exchange);
    var response = handleRequest(request);

    sendResponseAndClose(exchange, response);
  }


  private Request buildRequest(HttpExchange exchange) throws IOException {
    var body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    var queryParams = queryParser.parseToMap(exchange.getRequestURI().getQuery());

    return new Request(body, queryParams);
  }

  public abstract Response handleRequest(Request request);
}
