package infra.checkers.primitives;

import java.util.Random;

public class Vector {

  private final int x;
  private final int y;
  private final int z;

  public static final Vector zero = new Vector(0, 0, 0);
  public static final Vector xUnit = new Vector(1, 0, 0);
  public static final Vector yUnit = new Vector(0, 1, 0);
  public static final Vector zUnit = new Vector(0, 0, 1);

  private static final Random random = new Random();

  public Vector(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public static Vector create(int x, int y, int z) {
    return new Vector(x, y, z);
  }

  public static Vector createRandom(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
    var y = random.nextInt(Math.abs(maxY - minY)) + minY;
    var x = random.nextInt(Math.abs(maxX - minX)) + minX;
    var z = random.nextInt(Math.abs(maxZ - minZ)) + minZ;

    return new Vector(x, y, z);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
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
      return x == other.x && y == other.y && z == other.z;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return ((x * 1033) ^ y * 1033) ^ z;
  }

  @Override
  public String toString() {
    return String.format("(%d;%d;%d)", x, y, z);
  }

  public Vector add(Vector vector) {
    return new Vector(x + vector.x, y + vector.y, z + vector.z);
  }

  public Vector sub(Vector vector) {
    return new Vector(x - vector.x, y - vector.y, z - vector.z);
  }

  public Vector mul(int k) {
    return new Vector(x * k, y * k, z * k);
  }

  public int getManhattanLength() {
    var dx = Math.abs(x);
    var dy = Math.abs(y);
    var dz = Math.abs(z);

    return dx + dy + dz;
  }

  public int getChebushevLength() {
    var dx = Math.abs(x);
    var dy = Math.abs(y);
    var dz = Math.abs(z);

    return Math.max(Math.max(dy, dx), dz);
  }

  public static int getManhattanLength(Vector v1, Vector v2) {
    return v1.sub(v2).getManhattanLength();
  }

  public static int getChebushevLength(Vector v1, Vector v2) {
    return v1.sub(v2).getChebushevLength();
  }
}
