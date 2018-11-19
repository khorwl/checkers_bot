package server.api.handlers.get_session;

import core.checkers.game.GameState;
import core.checkers.primitives.Checker;
import java.util.List;

public class ClientSessionInfo {
  private final List<Checker> checkers;
  private final String id;
  private final String whitePlayerName;
  private final String blackPlayerName;
  private final GameState state;

  public ClientSessionInfo(
      List<Checker> checkers,
      String id,
      String whitePlayerName,
      String blackPlayerName, GameState state) {
    this.checkers = checkers;
    this.id = id;
    this.whitePlayerName = whitePlayerName;
    this.blackPlayerName = blackPlayerName;
    this.state = state;
  }

  public String getBlackPlayerName() {
    return blackPlayerName;
  }

  public String getWhitePlayerName() {
    return whitePlayerName;
  }

  public String getId() {
    return id;
  }

  public List<Checker> getCheckers() {
    return checkers;
  }

  public GameState getState() {
    return state;
  }
}
