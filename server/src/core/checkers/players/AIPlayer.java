package core.checkers.players;

public abstract class AIPlayer implements IPlayer {
  @Override
  public final boolean haveNextTurn() {
    return true;
  }
}
