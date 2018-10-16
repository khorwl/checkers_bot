import infra.Client;
import infra.CommandFactory;
import infra.CommandListGenerator;
import infra.sender.HttpRequestSender;
import infra.ui.ConsoleReader;
import infra.ui.ConsoleWriter;

public class Main {

  public static void main(String[] args) {

    try {
      var reader = new ConsoleReader();
      var writer = new ConsoleWriter();
      var sender = new HttpRequestSender("http://localhost:8600");
      var generator = new CommandListGenerator();
      var commands = generator.generateForAdmin(reader, writer, sender);
      var factory = new CommandFactory(commands);
      var client = new Client(reader, writer, factory);

      while (!client.hasFinished()) {
        client.processNextCommand();
      }
    } catch (Exception e) {
      System.out.println(String.format("Fatal error: %s", e.getMessage()));
      e.printStackTrace();
    }
  }
}