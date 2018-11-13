package core.checkers.players;

import core.checkers.IGame;
import core.checkers.primitives.Turn;
import core.tools.CoreException;

public interface IPlayer {
  void setGame(IGame game);
  boolean haveNextTurn();
  Turn getNextTurn() throws CoreException;
}
