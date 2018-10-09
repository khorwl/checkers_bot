package commands;

import infra.Environment;
import infra.ICommand;
import infra.sender.IRequestSender;
import infra.sender.Request;
import infra.ui.IStringReader;
import infra.ui.IStringWriter;
import java.util.HashMap;

public class LoginCommand implements ICommand {

  private IStringWriter writer;
  private IStringReader reader;
  private IRequestSender sender;

  public LoginCommand(
      IStringReader reader,
      IStringWriter writer,
      IRequestSender sender) {
    this.writer = writer;
    this.reader = reader;
    this.sender = sender;
  }

  @Override
  public void execute(Environment environment) {
    if (environment.getLoggedUsedName() != null)
    {
      writer.writeString(String.format("Already logged by %s", environment.getLoggedUsedName()));
      return;
    }

    writer.writeString("Enter user name: ");
    var name = reader.readNextString();

    var params = new HashMap<String, String>();
    params.put("name", name);
    var response = sender.sendRequest(new Request("/register", params, null));

    if (response.getStatusCode() == 200)
    {
      writer.writeString("Successfully logged in");
      environment.setLoggedUsedName(name);
    }
    else
    {
      writer.writeString("Cant login");
    }
  }

  @Override
  public String getName() {
    return "login";
  }
}
