package server.handlers;

import core.queue.IPlayerQueue;
import core.sessions.ISessionServer;
import core.userdb.IUserDataBase;
import server.Request;
import server.Response;
import tools.QueryParser;

public class GetStatus extends CommandHandler {

  public GetStatus(QueryParser queryParser, IUserDataBase userDataBase,
      ISessionServer sessionServer, IPlayerQueue playerQueue) {
    super(queryParser, userDataBase, sessionServer, playerQueue);
  }

  @Override
  public Response handleRequest(Request request) {
    return null;
  }
}
