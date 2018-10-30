package infra;

import infra.checkers.IMatchBoard;

public class Session {

  private final String id;
  private final User whitePlayer;
  private final User blackPlayer;
  private final IMatchBoard matchBoard;

  private SessionState state;
  private TurnOrder turnOrder;

  public Session(User whitePlayer, User blackPlayer, String id, IMatchBoard matchBoard) {
    this.whitePlayer = whitePlayer;
    this.blackPlayer = blackPlayer;
    this.id = id == null ? java.util.UUID.randomUUID().toString() : id;
    this.matchBoard = matchBoard;

    state = SessionState.IN_PROGRESS;
    turnOrder = TurnOrder.WHITE;
  }

  public User getWhitePlayer() {
    return whitePlayer;
  }

  public User getBlackPlayer() {
    return blackPlayer;
  }

  public String getId() {
    return id;
  }

  public SessionState getState() {
    return state;
  }

  public TurnOrder getTurnOrder() {
    return turnOrder;
  }

  public void setState(SessionState state) {
    this.state = state;
  }

  public void setTurnOrder(TurnOrder turnOrder) {
    this.turnOrder = turnOrder;
  }

  public IMatchBoard getMatchBoard() {
    return matchBoard;
  }
}

