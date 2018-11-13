package core.checkers.primitives;

import java.util.Random;

import tools.Pair;

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

    public static Vector create(int x, int y, int z) { return new Vector(x, y, z); }

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

    public static final Character NumberDelimiter = ',';

    @Override
    public String toString() {
        return String.format("(%d%c%d%c%d)", x, NumberDelimiter, y, NumberDelimiter, z);
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

    public static int getManhattanDistance(Vector v1, Vector v2) {
        return v1.sub(v2).getManhattanLength();
    }

    public static int getChebushevDistance(Vector v1, Vector v2) {
        return v1.sub(v2).getChebushevLength();
    }

    public static Vector parse(String string) throws VectorException {
        var p = tryParse(string);

        if (p.first()) //if successfully parsed
        {
            return p.second();
        }

        throw new VectorException(
                String.format("Illegal vector string format: %s",
                        string.length() < 50 ? string : string.substring(0, 45) + "..."));
    }

    private static final Pair<Boolean, Vector> failedParsePair = Pair.create(false, zero);

    public static Pair<Boolean, Vector> tryParse(String string) {
        try {
            var numbers = readNumbers(string);

            var x = Integer.decode(numbers[0]);
            var y = Integer.decode(numbers[1]);
            var z = Integer.decode(numbers[2]);

            return Pair.create(true, new Vector(x, y, z));
        } catch (NullPointerException | NumberFormatException e) {
            return failedParsePair;
        }
    }

    public Vector getUnit() {
        var signX = Integer.signum(x);
        var signY = Integer.signum(y);
        var signZ = Integer.signum(z);

        return new Vector(signX, signY, signZ);
    }

    private static String[] readNumbers(String string) {
        var index = 0;

        if (!string.startsWith("(") || !string.endsWith(")")) {
            return null;
        }

        string = string.substring(1, string.length() - 1);

        var tokens = new String[3];

        for (var i = 0; i < 3; i++) {
            var token = readNumber(string, index);

            if (token == null || token.length() == 0) {
                return null;
            }

            index += token.length();
            tokens[i] = token;

            if (i != 2) {
                if (index >= string.length() || string.charAt(index) != NumberDelimiter) {
                    return null;
                }

                index++;
            } else {
                if (index < string.length()) {
                    return null;
                }
            }
        }

        return tokens;
    }

    private static String readNumber(String string, int index) {
        if (index < 0 || index >= string.length()) {
            return null;
        }

        var builder = new StringBuilder();

        while (index < string.length()) {
            var ch = string.charAt(index);

            if (!Character.isDigit(ch) && ch != '-') {
                break;
            }

            builder.append(ch);

            index++;
        }

        return builder.toString();
    }
}
