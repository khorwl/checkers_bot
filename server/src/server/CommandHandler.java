package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import tools.QueryParser;

public abstract class CommandHandler implements HttpHandler {

  protected QueryParser queryParser;

  public CommandHandler() {
    queryParser = new QueryParser();
  }

  protected void sendResponseAndClose(HttpExchange httpExchange, String text, int statusCode)
      throws IOException {
    var bytes = text.getBytes(StandardCharsets.UTF_8);

    httpExchange.getResponseHeaders().add("encoding", "utf-8");
    httpExchange.sendResponseHeaders(statusCode, bytes.length);
    httpExchange.getResponseBody().write(bytes);
    httpExchange.close();
  }

  protected String getQueryParameterOrNull(HttpExchange httpExchange, String key) {
    var queryParams = queryParser.parseToMap(httpExchange.getRequestURI().getQuery());

    if (queryParams.containsKey(key)) {
      return queryParams.get(key);
    }

    return null;
  }
}
