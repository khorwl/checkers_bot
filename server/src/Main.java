import infra.CheckersServer;
import infra.sessions.PlayerQueue;
import infra.sessions.SessionServer;
import infra.sessions.UserDataBase;
import java.net.InetSocketAddress;
import server.Server;

public class Main {

  public static void main(String[] args) {
    try
    {
      var userDataBase = new UserDataBase();
      var sessionServer = new SessionServer(null);
      var playerQueue = new PlayerQueue();
      var checkersServer = new CheckersServer(userDataBase, sessionServer, playerQueue);
      var server = new Server(checkersServer);

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
