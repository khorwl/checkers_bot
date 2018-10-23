package server;

import com.sun.net.httpserver.HttpExchange;
import infra.IQuizServer;
import java.io.IOException;

public class HomeHandler extends CommandHandler {

  public HomeHandler(IQuizServer quizServer) {
    super(quizServer);
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    var response = "Hello, I am chat bot";
    sendResponseAndClose(httpExchange, response, HttpStatusCode.OK);
  }
}
