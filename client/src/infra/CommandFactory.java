package infra;

import java.util.List;

public class CommandFactory implements ICommandFactory
{
  private List<ICommand> commands;

  public CommandFactory(List<ICommand> commands) {
    this.commands = commands;
  }

  @Override
  public ICommand fromString(String command) {
    for (var cmd: commands) {
      if (cmd.getName().equalsIgnoreCase(command.trim()))
        return cmd;
    }

    return null;
  }
}
