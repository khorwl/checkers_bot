package tools;

import primitives.Direction;
import primitives.Vector;

public class DirectionHelper {

    public Vector getDeltaVector(Direction direction) {
        switch (direction) {
            case LEFT_DOWN:
                return new Vector(-1, -1);
            case LEFT_UP:
                return new Vector(1, -1);
            case RIGHT_DOWN:
                return new Vector(-1, 1);
            case RIGHT_UP:
                return new Vector(1, 1);
            default:
                throw new IllegalArgumentException();
        }
    }
}
