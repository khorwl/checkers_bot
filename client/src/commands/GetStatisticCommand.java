package commands;

import infra.Environment;
import infra.ICommand;
import infra.sender.IRequestSender;
import infra.sender.Request;
import infra.ui.IStringWriter;

public class GetStatisticCommand implements ICommand {
  private IStringWriter writer;
  private IRequestSender sender;

  public GetStatisticCommand(
      IStringWriter writer,
      IRequestSender sender) {
    this.writer = writer;
    this.sender = sender;
  }


  @Override
  public void execute(Environment environment) {
    var request = new Request("/get_statistic", null, null);
    var response = sender.sendRequest(request);

    if (response.getStatusCode() == 200)
      writer.writeString(response.getBody());
    else
      writer.writeString("Cant get statistic");
  }

  @Override
  public String getName() {
    return "statistic";
  }
}
