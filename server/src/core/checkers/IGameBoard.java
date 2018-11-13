package core.checkers;

import core.checkers.primitives.Checker;
import core.checkers.primitives.TurnStatus;
import core.checkers.primitives.Vector;
import java.util.List;

public interface IGameBoard {
  TurnStatus makeMove(Vector from, Vector to);
  Checker getCheckerAt(Vector position);
  List<Checker> getCheckers();
}
