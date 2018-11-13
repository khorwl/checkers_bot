package core.sessions;

import core.checkers.IGame;

public class Session {
  private final String id;
  private final User whitePlayer;
  private final User blackPlayer;

  private final IGame game;

  public Session(User whitePlayer, User blackPlayer, String id, IGame game) {
    this.whitePlayer = whitePlayer;
    this.blackPlayer = blackPlayer;
    this.id = id;
    this.game = game;
  }

  public User getWhitePlayer() {
    return whitePlayer;
  }

  public User getBlackPlayer() {
    return blackPlayer;
  }

  public String getId() {
    return id;
  }

  public IGame getGame() {
    return game;
  }
}

