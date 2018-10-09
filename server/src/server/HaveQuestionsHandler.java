package server;

import com.sun.net.httpserver.HttpExchange;
import infra.IQuizServer;

import java.io.IOException;
import java.security.KeyException;

public class HaveQuestionsHandler extends CommandHandler {

  public HaveQuestionsHandler(IQuizServer quizServer) {
    super(quizServer);
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    var name = getQueryParameterOrNull(httpExchange, "name");
    if (name == null) {
      sendResponseAndClose(httpExchange, "Invalid query", HttpStatusCode.BadRequest);
      return;
    }

    try {
      sendResponseAndClose(httpExchange, quizServer.haveQuestions(name) ? "true" : "false",
          HttpStatusCode.OK);
    } catch (KeyException e) {
      sendResponseAndClose(httpExchange, String.format("no such user %s", name),
          HttpStatusCode.NotFound);
    }
  }
}
