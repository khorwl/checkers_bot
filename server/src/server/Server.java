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

  public void run(InetSocketAddress addres) throws IOException {
    System.out.println(
        String.format("Starting server at %s:%s", addres.getHostString(), addres.getPort()));
    System.out.println("Use Ctrl+C to shutdown");

    HttpServer httpServer = HttpServer.create(addres, 0);

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