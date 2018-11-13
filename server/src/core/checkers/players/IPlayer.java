package core.checkers.players;

import core.checkers.primitives.Turn;

public interface IPlayer {
  boolean haveNextTurn();
  Turn getNextTurn();
}
