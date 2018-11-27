import com.google.inject.Guice;
import java.net.InetSocketAddress;
import server.Server;
import tools.injection.BasicModule;

public class Main {

  public static void main(String[] args) {
    try
    {
      var injector = Guice.createInjector(new BasicModule());
      var server = injector.getInstance(Server.class);
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
