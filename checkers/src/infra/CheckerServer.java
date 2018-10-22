package infra;

import primitives.*;

import java.util.ArrayList;

public class CheckerServer implements ICheckerServer {
    private ArrayList<Checker> locationCheckers;


    public CheckerServer(ArrayList<Checker> locationCheckers) {
        this.locationCheckers = locationCheckers;
    }

    @Override
    public ArrayList getLocationCheckers() {
        return locationCheckers;
    }

    @Override
    public Checker getCheckerPosition(Vector cords) {
        for (Checker locationChecker : locationCheckers) {
            if (locationChecker.getPosition().equals(cords))
                return locationChecker;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Status makeMove(Checker checker, Vector delta) {
        if (Math.abs(delta.getY()) != Math.abs(delta.getX()))
            return Status.FAIL;
        if (isCutDown(checker, delta))
            return cutDown(checker, delta);
        if (isStep(checker, delta))
            return makeStep(checker, delta);
        return Status.FAIL;
    }

    @Override
    public Status makeMove(Vector from, Vector to) {
        var checker = getCheckerPosition(from);
        var delta = to.sub(from);
        return makeMove(checker, delta);
    }

    private Status cutDown(Checker checker, Vector delta) {
        if (isValidCutDown(checker, delta)) {
            var enemyChecker = getCheckerOnWay(checker.getPosition(), delta);
            locationCheckers.remove(enemyChecker);
            checker.move(delta);
            return Status.SUCCESS;
        }
        return Status.FAIL;
    }

    private boolean isCutDown(Checker checker, Vector delta) {
        if (countCheckersOnWay(checker, delta) == 1) {
            return true;
        }
        return false;
    }

    private boolean isStep(Checker checker, Vector delta) {
        if (isFreeWay(checker, delta)) {
            return true;
        }
        return false;
    }

    private boolean isValidCutDown(Checker checker, Vector delta) {
        var nextPosition = checker.getPosition().add(delta);

        if (checker.getRank() == Rank.SOLDIER && ((Math.abs(delta.getY()) != 2)))
            return false;

        if (isFreeCell(nextPosition)) {
            var checkers = getCheckerOnWay(checker.getPosition(), delta);
            return checkers.size() == 1 && isEnemyChecker(checkers.get(0), checker);
        }
        return false;
    }

    private boolean isFreeWay(Checker checker, Vector delta) {
        return countCheckersOnWay(checker, delta) == 0;
    }

    private int countCheckersOnWay(Checker checker, Vector delta) {
        return getCheckerOnWay(checker.getPosition(), delta).size();
    }

    private ArrayList<Checker> getCheckerOnWay(Vector startVector, Vector delta) {
        var pointer = new Checker(startVector, Color.NEUTRAL, Rank.SOLDIER);
        var lengthWay = Math.sqrt(delta.getY() * delta.getY());
        var unitVector = createUnitVector(delta);
        var checkers = new ArrayList<Checker>();

        for (var i = 0; i < lengthWay; i++) {
            if (makeStep(pointer, unitVector) != Status.SUCCESS) {
                var enemyCheckerCords = pointer.getPosition().add(unitVector);
                checkers.add(getCheckerPosition(enemyCheckerCords));
                pointer.setPosition(enemyCheckerCords);
            }
        }
        return checkers;
    }

    private Vector createUnitVector(Vector delta) {
        var length = Math.sqrt(delta.getX() * delta.getX() + delta.getY() * delta.getY());
        var x = (int) (delta.getX() / length * Math.sqrt(2));
        var y = (int) (delta.getY() / length * Math.sqrt(2));

        return new Vector(y, x);
    }

    private Status makeStep(Checker checker, Vector delta) {
        if (!isValidStep(checker, delta)) {
            return Status.FAIL;
        }
        checker.move(delta);
        return Status.SUCCESS;
    }

    private boolean isValidStep(Checker checker, Vector delta) {
        var nextPosition = checker.getPosition().add(delta);

        if (checker.getRank() == Rank.SOLDIER) {
            if ((Math.abs(delta.getY()) != 1))
                return false;
        }
        if (nextPosition.getY() < 0 || nextPosition.getX() < 0)
            return false;

        return isFreeCell(nextPosition);
    }

    private boolean isFreeCell(Vector cords) {
        for (Checker locationChecker : locationCheckers) {
            if (locationChecker.getPosition().equals(cords))
                return false;
        }
        return true;
    }

    private boolean isEnemyChecker(Checker source, Checker destination) {
        return !source.getColor().equals(destination.getColor());
    }
}
