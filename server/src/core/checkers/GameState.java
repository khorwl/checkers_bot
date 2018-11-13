package core.checkers;

import core.checkers.primitives.Checker;
import core.checkers.primitives.Color;
import core.checkers.primitives.GameProgress;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {
  private GameProgress progress;
  private List<Checker> checkers;
  private Color nextTurnOrder;

  public GameState(List<Checker> checkers) {
    this.checkers = checkers;
    nextTurnOrder = Color.WHITE;
    progress = GameProgress.IN_PROGRESS;
  }

  public List<Checker> checkers() {
    return checkers;
  }

  public Collection<Checker> whiteCheckers() {
    return getCheckersWithColor(Color.WHITE);
  }

  public Collection<Checker> blackCheckers() {
    return getCheckersWithColor(Color.BLACK);
  }

  private Collection<Checker> getCheckersWithColor(Color color) {
    return checkers.stream().filter(ch -> ch.getColor() == color).collect(Collectors.toList());
  }
}
