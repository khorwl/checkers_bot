package core.checkers.primitives;

public class Checker {

    private Vector position;
    private Color color;
    private Rank rank;

    public Checker(Vector vector, Color color, Rank rank) {
        if (!vector.inBoard()) {
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

        if (!next.inBoard()) {
            throw new IllegalArgumentException();
        }

        position = next;
    }

    public void setPosition(Vector vector) {
        if (!vector.inBoard()) {
            throw new IllegalArgumentException();
        }

        position = vector;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj instanceof Checker) {
            var other = (Checker) obj;
            return color == other.color && rank == other.rank && position.equals(other.position);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (color.hashCode() *1033) ^ rank.hashCode() ^ position.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Checker %s %s at %s", color.toString(), rank.toString(), position.toString());
    }
}