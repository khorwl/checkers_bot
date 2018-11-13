package core.checkers.primitives;

public class Turn {
  private final Vector from;
  private final Vector to;

  public Turn(Vector from, Vector to) {
    this.from = from;
    this.to = to;
  }

  public Vector from() {
    return from;
  }

  public Vector to() {
    return to;
  }
}
