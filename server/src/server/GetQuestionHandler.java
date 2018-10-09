package server;

import com.sun.net.httpserver.HttpExchange;
import infra.IQuizServer;
import infra.quiz.Question;
import tools.QuestionSerializer;

import java.io.IOException;
import java.security.KeyException;

public class GetQuestionHandler extends CommandHandler {

  private QuestionSerializer serializer;

  public GetQuestionHandler(IQuizServer quizServer) {
    super(quizServer);
    serializer = new QuestionSerializer();
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    var name = getQueryParameterOrNull(httpExchange, "name");
    if (name == null) {
      sendResponseAndClose(httpExchange, "Invalid query", HttpStatusCode.BadRequest);
      return;
    }

    try {
      if (!quizServer.haveQuestions(name)) {
        sendResponseAndClose(httpExchange, String.format("no questions for user %s", name),
            HttpStatusCode.BadRequest);
        return;
      }

      var question = quizServer.getQuestion(name);
      var data = String.format("%s-%s", question.getId(), question.getQuestion());
      sendResponseAndClose(httpExchange, data, HttpStatusCode.OK);
    } catch (KeyException e) {
      sendResponseAndClose(httpExchange, String.format("no such user %s", name),
          HttpStatusCode.NotFound);
    }
  }
}
