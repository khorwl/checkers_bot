package infra.checkers;

import infra.checkers.primitives.Checker;
import infra.checkers.primitives.TurnStatus;
import infra.checkers.primitives.Vector;
import java.util.List;

public interface IGameBoard {
  TurnStatus makeMove(Vector from, Vector to);
  Checker getCheckerAt(Vector position);
  List<Checker> getCheckers();
}
