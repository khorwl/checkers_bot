import java.net.InetSocketAddress;
import server.ServerFactory;
import server.api.handlers.HandlersLoader;

public class Main {

  public static void main(String[] args) {
    try
    {
      var factory = new ServerFactory();
      var server = factory.create();

      server.run(new InetSocketAddress("0.0.0.0",8600));
    }
    catch (Exception e)
    {
      System.out.println("During server work, exception was thrown");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
