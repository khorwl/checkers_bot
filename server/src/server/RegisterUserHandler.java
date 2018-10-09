package server;

import com.sun.net.httpserver.HttpExchange;
import infra.IQuizServer;

import java.io.IOException;

public class RegisterUserHandler extends CommandHandler {

  RegisterUserHandler(IQuizServer server) {
    super(server);
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    var name = getQueryParameterOrNull(httpExchange, "name");

    if (name == null) {
      sendResponseAndClose(httpExchange, "Invalid query parameters", HttpStatusCode.BadRequest);
      return;
    }

    if (quizServer.registerUser(name)) {
      sendResponseAndClose(httpExchange, String.format("Successfully add new user %s", name),
          HttpStatusCode.OK);
    } else {
      sendResponseAndClose(httpExchange, String.format("User %s already exists", name),
          HttpStatusCode.Conflict);
    }
  }
}
