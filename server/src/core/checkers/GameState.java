package core.checkers;

import core.checkers.primitives.Checker;
import core.checkers.primitives.Color;
import core.checkers.primitives.GameProgress;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

  public void setProgress(GameProgress progress){
    this.progress = progress;
  }

  public Color getNextTurnOrder() {
    return nextTurnOrder;
  }

  public void changeNextTurnOrder(){
    nextTurnOrder = nextTurnOrder == Color.BLACK ? Color.WHITE : Color.BLACK;
  }
}
