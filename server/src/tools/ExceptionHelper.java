package tools;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHelper {
  public static String getStackTraceAsString(Throwable exception) {
    StringWriter sw = new StringWriter();
    exception.printStackTrace(new PrintWriter(sw));

    return sw.toString();
  }
}
