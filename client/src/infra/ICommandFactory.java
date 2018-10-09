package infra;

import infra.executors.ICommand;

public interface ICommandFactory {
  ICommand getCommandFromString(String command);
}
