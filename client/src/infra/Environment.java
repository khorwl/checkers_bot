package infra;

public class Environment {
  private String loggedUsedName;
  private boolean hasExited;

  public Environment()
  {
    hasExited = false;
    loggedUsedName = null;
  }

  public String getLoggedUsedName() {
    return loggedUsedName;
  }

  public void setLoggedUsedName(String loggedUsedName) {
    this.loggedUsedName = loggedUsedName;
  }

  public boolean hasExited() {
    return hasExited;
  }

  public void hasExited(boolean hasExited) {
    this.hasExited = hasExited;
  }
}
