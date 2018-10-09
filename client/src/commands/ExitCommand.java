package commands;

import infra.Environment;
import infra.ICommand;
import infra.sender.IRequestSender;
import infra.sender.Request;
import java.util.HashMap;

public class ExitCommand implements ICommand {
  private IRequestSender sender;

  public ExitCommand(IRequestSender sender) {
    this.sender = sender;
  }

  @Override
  public void execute(Environment environment) {
    if (environment.getLoggedUsedName() != null)
    {
      var name = environment.getLoggedUsedName();
      var params = new HashMap<String, String>();
      params.put("name", name);
      var request = new Request("/delete", params, null);

      sender.sendRequest(request);
    }

    environment.hasExited(true);
  }

  @Override
  public String getName() {
    return "exit";
  }
}
