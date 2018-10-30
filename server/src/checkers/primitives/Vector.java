package checkers.primitives;

import java.util.Random;

public class Vector {

  private final int x;
  private final int y;

  public static final Vector zero = new Vector(0, 0);
  public static final Vector xUnit = new Vector(0, 1);
  public static final Vector yUnit = new Vector(1, 0);

  private static final Random random = new Random();

  public Vector(int y, int x) {
    this.x = x;
    this.y = y;
  }

  public static Vector create(int y, int x) {
    return new Vector(y, x);
  }

  public static Vector createRandom(int minY, int maxY, int minX, int maxX) {
    var y = random.nextInt(Math.abs(maxY - minY)) + minY;
    var x = random.nextInt(Math.abs(maxX - minX)) + minX;

    return new Vector(y, x);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj == this) {
      return true;
    }

    if (obj instanceof Vector) {
      var other = (Vector) obj;
      return x == other.x && y == other.y;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return (x * 1033) ^ y;
  }

  @Override
  public String toString() {
    return String.format("(%d;%d)", y, x);
  }

  public Vector add(Vector vector) {
    return new Vector(y + vector.y, x + vector.x);
  }

  public Vector sub(Vector vector) {
    return new Vector(y - vector.y, x - vector.x);
  }

  public Vector mul(int k) {
    return new Vector(y * k, x * k);
  }

  public int getManhattanLength() {
    var dx = Math.abs(x);
    var dy = Math.abs(y);

    return dx + dy;
  }

  public int getChebushevLength() {
    var dx = Math.abs(x);
    var dy = Math.abs(y);

    return Math.max(dy, dx);
  }

  public static int getManhattanLength(Vector v1, Vector v2) {
    return v1.sub(v2).getManhattanLength();
  }

  public static int getChebushevLength(Vector v1, Vector v2) {
    return v1.sub(v2).getChebushevLength();
  }
}
