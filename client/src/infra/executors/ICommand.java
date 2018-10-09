package infra.executors;

import infra.Environment;
import infra.sender.IRequestSender;

public interface ICommand {
  void execute(IRequestSender requestSender, Environment environment);
  String getName();
}
