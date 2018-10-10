package server;

import com.sun.net.httpserver.HttpExchange;
import infra.IQuizServer;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetStatisticHandler extends CommandHandler {

  public GetStatisticHandler(IQuizServer quizServer) {
    super(quizServer);
  }

  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
    var data = prepareData(quizServer.getStatistic());

    sendResponseAndClose(httpExchange, data, HttpStatusCode.OK);
  }

  private String prepareData(Map<String, List<String>> data) {
    var dataBuilder = new StringBuilder();

    for (var name : data.keySet()) {
      var list = data.get(name);
      dataBuilder.append(name).append(": ");
      for (var id: list) {
        dataBuilder.append(id).append(" ");
      }
      dataBuilder.append("\r\n");
    }

    return dataBuilder.toString();
  }
}
