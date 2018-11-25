package core.checkers.game;

import core.checkers.primitives.Color;
import core.checkers.primitives.GameProgress;

public class GameState {

  private GameProgress progress;
  private Color nextTurnOrder;

  public GameState() {
    nextTurnOrder = Color.WHITE;
    progress = GameProgress.IN_PROGRESS;
  }

  public GameProgress getProgress() {
    return progress;
  }

  public void setProgress(GameProgress progress) {
    this.progress = progress;
  }

  public Color getNextTurnOrder() {
    return nextTurnOrder;
  }

  public void changeTurnOrderToNext() {
    nextTurnOrder = nextTurnOrder == Color.BLACK ? Color.WHITE : Color.BLACK;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj == this) {
      return true;
    }

    if (obj instanceof GameState) {
      var other = (GameState) obj;
      return other.progress == progress && other.nextTurnOrder == nextTurnOrder;
    }

    return false;
  }
}
