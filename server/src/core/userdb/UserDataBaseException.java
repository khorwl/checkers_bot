package core.userdb;

import core.tools.CoreException;

public class UserDataBaseException extends CoreException {
  public UserDataBaseException() {
    super("User database exception");
  }

  public UserDataBaseException(String message) {
    super(message);
  }
}
