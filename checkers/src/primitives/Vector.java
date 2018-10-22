package primitives;

public class Vector {
    private int x;
    private int y;

    public Vector(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector add(Vector v) {
        return new Vector(y + v.y, x + v.x);
    }

    public Vector sub(Vector v){
        return  new Vector(y - v.y, x- v.x);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        var compared = (Vector) obj;

        return compared.getX() == x && compared.getY() == y;
    }
}
