package server;

import com.sun.net.httpserver.HttpExchange;
import infra.IQuizServer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyException;

public class SubmitAnswerHandler extends CommandHandler {

  public SubmitAnswerHandler(IQuizServer quizServer) {
    super(quizServer);
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    var name = getQueryParameterOrNull(httpExchange, "name");
    var id = getQueryParameterOrNull(httpExchange, "id");

    if (name == null || id == null) {
      sendResponseAndClose(httpExchange, "invalid query", HttpStatusCode.BadRequest);
      return;
    }

    try {
      var answer = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
      sendResponseAndClose(httpExchange,
          quizServer.submitAnswer(name, id, answer) ? "true" : "false", HttpStatusCode.OK);
    } catch (KeyException e) {
      sendResponseAndClose(httpExchange, String.format("no such user %s", name),
          HttpStatusCode.NotFound);
    }
  }
}
