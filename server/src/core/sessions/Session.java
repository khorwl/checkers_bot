package core.sessions;

import core.checkers.IGame;
import core.userdb.User;

public class Session {
  private final String id;
  private final IGame game;

  public Session(String id, IGame game) {
    this.id = id;
    this.game = game;
  }

  public String getId() {
    return id;
  }

  public IGame getGame() {
    return game;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;

    if (this == obj)
      return true;

    if (obj instanceof Session) {
      var other = (Session)obj;

      return id.equals(other.id);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return String.format("Session %s", id);
  }
}

