package infra;

import infra.sender.IRequestSender;
import infra.ui.IStringReader;
import infra.ui.IStringWriter;

public interface ICommand {
  void execute(Environment environment);
  String getName();
}
