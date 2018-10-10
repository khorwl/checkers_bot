package infra;

import commands.AskCommand;
import commands.ExitCommand;
import commands.GetStatisticCommand;
import commands.LoginCommand;
import infra.sender.IRequestSender;
import infra.ui.IStringReader;
import infra.ui.IStringWriter;
import java.util.ArrayList;
import java.util.List;

public class CommandListGenerator {

  public List<ICommand> generate(
      IStringReader reader,
      IStringWriter writer,
      IRequestSender sender) {
    return generateCommonPart(reader, writer, sender);
  }

  public List<ICommand> generateForAdmin(
      IStringReader reader,
      IStringWriter writer,
      IRequestSender sender) {
    var common = generateCommonPart(reader, writer, sender);

    common.add(new GetStatisticCommand(writer, sender));

    return common;
  }

  private ArrayList<ICommand> generateCommonPart(
      IStringReader reader,
      IStringWriter writer,
      IRequestSender sender)
  {
    var list = new ArrayList<ICommand>();

    list.add(new AskCommand(reader, writer, sender));
    list.add(new LoginCommand(reader, writer, sender));
    list.add(new ExitCommand(sender));

    return list;
  }
}
