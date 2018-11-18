package server.api.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import core.checkers.ICheckersServer;
import core.queue.PlayerQueueException;
import core.sessions.SessionServerException;
import core.tools.CoreException;
import core.userdb.UserDataBaseException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import server.api.http.HttpRequest;
import server.api.http.HttpResponse;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import tools.ExceptionHelper;
import tools.QueryParser;

public abstract class Handler<T> implements HttpHandler {

  protected final QueryParser queryParser;
  protected final ICheckersServer server;

  public Handler(QueryParser queryParser, ICheckersServer server) {
    this.queryParser = queryParser;
    this.server = server;
  }

  private void sendResponseAndClose(HttpExchange httpExchange, String body, int statusCode)
      throws IOException {
    var bytes = body != null ? body.getBytes(StandardCharsets.UTF_8) : new byte[0];

    httpExchange.getResponseHeaders().add("encoding", "utf-8");
    httpExchange.getResponseHeaders().add("Content-Type", "application/json");
    httpExchange.sendResponseHeaders(statusCode, bytes.length);
    httpExchange.getResponseBody().write(bytes);
    httpExchange.close();
  }

  private void sendResponseAndClose(HttpExchange httpExchange, HttpResponse httpResponse)
      throws IOException {
    sendResponseAndClose(httpExchange, httpResponse.getBody(), httpResponse.getStatusCode());
  }

  public final void handle(HttpExchange exchange) throws IOException {
    var request = buildRequest(exchange);
    var response = getResponse(request);
    var asHttp = response.toHttpResponse();

    sendResponseAndClose(exchange, asHttp);
  }

  private final Response<T> getResponse(HttpRequest request) {
    try {
      return handleRequest(request);
    } catch (NoThatParameterException e) {
      var msg = String.format("Invalid query: %s", e.getMessage());

      return Response.createInvalidRequest(msg,null);
    } catch (UserDataBaseException | SessionServerException | PlayerQueueException e) {
      return Response.createFail(e.getMessage(), null);
    } catch (CoreException e) {
      var msg = String.format("CoreException %s:\n%s",
          e.getMessage(), ExceptionHelper.getStackTraceAsString(e));

      return Response.createInternalError(msg, null);
    } catch (Exception e) {
      var msg = String.format("Exception was thrown %s:\n%s",
          e.getMessage(), ExceptionHelper.getStackTraceAsString(e));

      return Response.createInternalError(msg, null);
    }
  }

  private HttpRequest buildRequest(HttpExchange exchange) throws IOException {
    var body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    var queryParams = queryParser.parseToMap(exchange.getRequestURI().getQuery());

    return new HttpRequest(body, queryParams);
  }

  public abstract Response<T> handleRequest(HttpRequest httpRequest) throws CoreException, NoThatParameterException;

  public abstract String getName();
}
