package server.api.handlers.get_session;

import core.ICheckersServer;
import server.api.handlers.CommandHandler;
import server.api.http.HttpRequest;
import server.api.response.Response;
import tools.QueryParser;

public class GetSession extends CommandHandler<SessionInfo> {

  public GetSession(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<SessionInfo> handleRequest(HttpRequest httpRequest) {
    return null;
  }
}
