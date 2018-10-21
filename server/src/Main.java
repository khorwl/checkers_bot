import infra.QuizServer;
import server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

  public static void main(String[] args) throws IOException {
    var server = new Server(new QuizServer());

    server.run(new InetSocketAddress("0.0.0.0",8600));
  }
}
