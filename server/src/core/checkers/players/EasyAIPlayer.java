package core.checkers.players;

import core.checkers.game.IGame;
import core.checkers.primitives.Turn;
import core.tools.CoreException;

public class EasyAIPlayer extends AIPlayer {

  private IGame game;

  public EasyAIPlayer() {
    this.game = null;
  }

  public EasyAIPlayer(IGame game) {
    this.game = game;
  }

  @Override
  public Turn getNextTurn() throws CoreException {
    if (game == null) {
      throw new CoreException("no game to get turn");
    }

    throw new CoreException("easy ai not realized");
  }
}
