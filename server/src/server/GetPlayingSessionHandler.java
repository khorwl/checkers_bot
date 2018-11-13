package server;

import core.ICheckersServer;

public class GetPlayingSessionHandler extends CommandHandler {

  public GetPlayingSessionHandler(ICheckersServer checkersServer) {
    super(checkersServer);
  }

  @Override
  public Response handleRequest(Request request) {
    return new Response("get session", HttpStatusCode.OK);
  }
}
