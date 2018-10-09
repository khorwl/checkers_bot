package infra;

import commands.AskCommand;
import commands.ExitCommand;
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
    return generate(reader, writer, sender);
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
