import infra.QuizServer;
import java.net.InetSocketAddress;
import server.Server;

public class Main {

  public static void main(String[] args) {
    try
    {
      var server = new Server(new QuizServer());

      server.run(new InetSocketAddress("0.0.0.0",8600));
    }
    catch (Exception e)
    {
      System.out.println("During server working error was thrown");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
