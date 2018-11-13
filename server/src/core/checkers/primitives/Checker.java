package core.checkers.primitives;

public class Checker {

  private Vector position;
  private Color color;
  private Rank rank;

  public Checker(Vector vector, Color color, Rank rank) {
    if (vector.getY() < 0 || vector.getX() < 0 || vector.getZ() < 0) {
      throw new IllegalArgumentException();
    }

    this.position = vector;
    this.color = color;
    this.rank = rank;
  }

  public Vector getPosition() {
    return position;
  }

  public Color getColor() {
    return color;
  }

  public Rank getRank() {
    return rank;
  }

  public void move(Vector delta) {
    var next = position.add(delta);

    if (next.getX() < 0 || next.getY() < 0 || next.getZ() < 0) {
      throw new IllegalArgumentException();
    }

    position = next;
  }


  public void setPosition(Vector vector) {
    if (vector.getY() < 0 || vector.getX() < 0 || vector.getZ() < 0) {
      throw new IllegalArgumentException();
    }

    position = vector;
  }
}