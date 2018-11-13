package core.userdb;

import core.checkers.IGame;
import core.checkers.players.IPlayer;
import core.checkers.primitives.Turn;
import core.tools.CoreException;

public class User implements IPlayer {

  private final String name;
  private int score;
  private Turn nextTurn;

  public User(String name) {
    this.name = name;
    score = 0;
    nextTurn = null;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public void addScore(int score) {
    this.score += score;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj == this) {
      return true;
    }

    if (obj instanceof User) {
      var other = (User)obj;

      return name.equals(other.name);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public void setGame(IGame game) {
  }

  @Override
  public boolean haveNextTurn() {
    return nextTurn != null;
  }

  @Override
  public Turn getNextTurn() throws CoreException {
    if (nextTurn == null) {
      throw new CoreException("No turn presented");
    }

    var turn = nextTurn;

    nextTurn = null;

    return turn;
  }
}