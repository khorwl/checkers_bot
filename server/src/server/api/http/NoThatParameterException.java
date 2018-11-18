package server.api.http;

public class NoThatParameterException extends Exception {
  public NoThatParameterException() {
    this("No that parameter");
  }

  public NoThatParameterException(String msg) {
    super(msg);
  }
}
