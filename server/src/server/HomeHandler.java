package server;

import core.ICheckersServer;

public class HomeHandler extends CommandHandler {

  public HomeHandler(ICheckersServer checkersServer) {
    super(checkersServer);
  }

  @Override
  public Response handleRequest(Request request) {
    return new Response(
        "This is service for play 3d checkers\n" + request.getBody() + "\n",
        HttpStatusCode.OK);
  }
}
