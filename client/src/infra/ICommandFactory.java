package infra;

import infra.sender.IRequestSender;

public interface ICommandFactory {
  ICommand fromString(String command);
}

