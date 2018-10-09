package infra;

import infra.sender.IRequestSender;
import infra.ui.IStringReader;
import infra.ui.IStringWriter;
import java.io.InvalidObjectException;

public class Client implements IClient {

  private IStringReader stringReader;
  private IStringWriter stringWriter;
  private ICommandFactory commandFactory;
  private Environment environment;

  public Client(
      IStringReader stringReader,
      IStringWriter stringWriter,
      ICommandFactory commandFactory) {
    this.stringReader = stringReader;
    this.stringWriter = stringWriter;
    this.commandFactory = commandFactory;
    environment = new Environment();
  }

  @Override
  public void processNextCommand() {
    if (!stringReader.hasNextString())
    {
      environment.hasExited(true);
      return;
    }

    var strCommand = stringReader.readNextString();
    var command = commandFactory.fromString(strCommand);

    if (command != null)
      command.execute(environment);
    else
      stringWriter.writeString(String.format("Unknown command: %s", strCommand));
  }

  @Override
  public boolean hasFinished() {
    return environment.hasExited();
  }
}
