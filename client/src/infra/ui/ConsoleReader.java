package infra.ui;

import java.io.Closeable;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleReader implements IStringReader, Closeable {
  private Scanner scanner;

  public ConsoleReader()
  {
    scanner = new Scanner(System.in);
  }

  @Override
  public String readNextString()
  {
    return scanner.nextLine();
  }

  @Override
  public boolean hasNextString()
  {
    return scanner.hasNextLine();
  }

  @Override
  public void close() throws IOException {
    scanner.close();
  }
}
