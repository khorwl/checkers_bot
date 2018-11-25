package core.checkers.players;

import core.checkers.primitives.Turn;
import core.tools.CoreException;

public interface IPlayer {
  boolean haveNextTurn();
  Turn getNextTurn() throws CoreException;
}
