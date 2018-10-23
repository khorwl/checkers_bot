package server;

import com.sun.net.httpserver.HttpExchange;
import infra.IQuizServer;
import java.io.IOException;

public class DeleteUserHandler extends CommandHandler {

  public DeleteUserHandler(IQuizServer quizServer) {
    super(quizServer);
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    var name = getQueryParameterOrNull(httpExchange, "name");

    if (name == null) {
      sendResponseAndClose(httpExchange, "Invalid query parameters", HttpStatusCode.BadRequest);
      return;
    }

    if (quizServer.deleteUser(name)) {
      sendResponseAndClose(httpExchange, String.format("Successfully delete user %s", name),
          HttpStatusCode.OK);
    } else {
      sendResponseAndClose(httpExchange, String.format("User %s not exists", name),
          HttpStatusCode.BadRequest);
    }
  }
}
