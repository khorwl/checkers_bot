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

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;

    if (this == obj)
      return true;

    if (obj instanceof Turn) {
      var other = (Turn)obj;

      return from.equals(other.from) && to.equals(other.to);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return from.hashCode() ^ to.hashCode();
  }
}
