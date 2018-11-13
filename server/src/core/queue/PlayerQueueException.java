package core.queue;

import core.tools.CoreException;

public class PlayerQueueException extends CoreException {
  public PlayerQueueException() {
    super("Player queue error");
  }

  public PlayerQueueException(String message) {
    super(message);
  }
}
