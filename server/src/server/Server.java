package server;

import com.sun.net.httpserver.HttpServer;
import infra.IQuizServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

  private IQuizServer quizServer;

  public Server(IQuizServer quizServer) {
    this.quizServer = quizServer;
  }

  public void run(InetSocketAddress inetSocketAddress) throws IOException {
    HttpServer httpServer = HttpServer.create(inetSocketAddress, 0);

    httpServer.createContext("/", new HomeHandler(quizServer));
    httpServer.createContext("/register", new RegisterUserHandler(quizServer));
    httpServer.createContext("/delete", new DeleteUserHandler(quizServer));
    httpServer.createContext("/have_questions", new HaveQuestionsHandler(quizServer));
    httpServer.createContext("/get_question", new GetQuestionHandler(quizServer));
    httpServer.createContext("/submit", new SubmitAnswerHandler(quizServer));
    httpServer.createContext("/get_statistic", new GetStatisticHandler(quizServer));
    httpServer.setExecutor(null);

    httpServer.start();
  }
}