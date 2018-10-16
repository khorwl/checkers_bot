package infra;

public class Environment {
  private String loggedUserName;
  private boolean hasExited;

  public Environment()
  {
    hasExited = false;
    loggedUserName = null;
  }

  public String getLoggedUserName() {
    return loggedUserName;
  }

  public void setLoggedUserName(String loggedUserName) {
    this.loggedUserName = loggedUserName;
  }

  public boolean hasExited() {
    return hasExited;
  }

  public void hasExited(boolean hasExited) {
    this.hasExited = hasExited;
  }
}
