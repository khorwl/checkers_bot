package core.sessions;

import core.tools.CoreException;

public class SessionServerException extends CoreException {
  public SessionServerException() {
    super("Session server error");
  }

  public SessionServerException(String message) {
    super(message);
  }
}
