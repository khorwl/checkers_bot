package infra;

import java.io.InvalidObjectException;

public class Environment {

  private boolean haveAnotherQuestion;
  private String loggedUserName;

  public Environment() {
    haveAnotherQuestion = false;
    loggedUserName = null;
  }

  public void login(String name) throws InvalidObjectException {
    if (loggedUserName != null)
      throw new InvalidObjectException(String.format("already logged by %s", loggedUserName));

    loggedUserName = name;
  }

  public String getLoggedUserName() { return loggedUserName; }

  public boolean haveAnotherQuestion() {
    return haveAnotherQuestion;
  }

  public void setNoQuestionsLeft() {
    haveAnotherQuestion = false;
  }
}
