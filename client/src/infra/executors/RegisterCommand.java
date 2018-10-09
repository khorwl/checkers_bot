package infra.executors;

import infra.Environment;
import infra.sender.IRequestSender;

public class RegisterCommand implements ICommand {

  @Override
  public void execute(IRequestSender requestSender, Environment environment) {

  }

  @Override
  public String getName() {
    return "register";
  }
}
