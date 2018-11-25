package server.api.handlers.register;

import core.ICheckersServer;
import server.api.handlers.Handler;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import tools.QueryParser;

public class Register extends Handler<Boolean> {

  public Register(QueryParser queryParser, ICheckersServer server) {
    super(queryParser, server);
  }

  @Override
  public Response<Boolean> handleRequest(HttpRequest httpRequest) throws NoThatParameterException {
    var name = httpRequest.getParameter("name");

    if (server.userDataBase().register(name))
      return Response.createSuccess(String.format("Successfully register user %s", name), true);

    return Response.createSuccess(String.format("Cant register user %s", name), false);
  }

  @Override
  public String getName() {
    return "register";
  }
}
