package infra.checkers;

import infra.checkers.primitives.Color;
import infra.checkers.primitives.GameState;
import infra.checkers.primitives.TurnStatus;
import infra.checkers.primitives.Vector;

public interface IGame {
  Color getTurnOrder();
  GameState getState();
  TurnStatus makeNextTurn(Vector from, Vector to);
}
