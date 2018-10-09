package infra.ui;

public class ConsoleWriter implements IStringWriter {

  @Override
  public void writeString(String string) {
    System.out.println(string);
  }
}
