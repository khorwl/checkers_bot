package infra;

import infra.executors.ICommand;
import java.util.List;

public class CommandFactory implements ICommandFactory {
  private List<ICommand> commands;

  public CommandFactory(List<ICommand> commands)
  {
    this.commands = commands;
  }

  @Override
  public ICommand getCommandFromString(String command) {
    return null;
  }
}
