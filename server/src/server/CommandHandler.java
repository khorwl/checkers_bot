package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import infra.IQuizServer;
import tools.QueryParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class CommandHandler implements HttpHandler {

  protected IQuizServer quizServer;
  protected QueryParser queryParser;

  public CommandHandler(IQuizServer quizServer) {
    this.quizServer = quizServer;
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
