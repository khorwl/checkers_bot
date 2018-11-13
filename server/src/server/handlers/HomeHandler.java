package server.handlers;

import core.queue.IPlayerQueue;
import core.sessions.ISessionServer;
import core.userdb.IUserDataBase;
import server.HttpStatusCode;
import server.Request;
import server.Response;
import tools.QueryParser;

public class HomeHandler extends CommandHandler {

  public HomeHandler(QueryParser queryParser, IUserDataBase userDataBase,
      ISessionServer sessionServer, IPlayerQueue playerQueue) {
    super(queryParser, userDataBase, sessionServer, playerQueue);
  }

  @Override
  public Response handleRequest(Request request) {
    return new Response("Checkers web service", HttpStatusCode.OK);
  }
}
