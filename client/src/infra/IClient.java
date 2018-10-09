package infra;

import java.io.InvalidObjectException;

public interface IClient {
  void processNextCommand() throws InvalidObjectException;
  boolean hasFinished();
}
