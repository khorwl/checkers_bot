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
    public Checker getCheckerByCords(Vector cords) {
        for (Checker locationChecker : locationCheckers) {
            if (locationChecker.getPosition().equals(cords))
                return locationChecker;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Status makeMove(Checker checker, Vector delta) {
        if (delta.getY() != delta.getX())
            return Status.FAIL;
        if (isCutDown(checker, delta))
            return cutDown(checker, delta);
        if (isStep(checker, delta))
            return makeStep(checker, delta);
        return Status.FAIL;
    }

    private Status makeStep(Checker checker, Vector delta) {
        if (checker.getRank() == Rank.SOLDIER)
            return makeStepSoldier(checker, delta);
        if (checker.getRank() == Rank.LADY)
            return makeStepLady(checker, delta);
        return Status.FAIL;
    }

    private Status cutDown(Checker checker, Vector delta) {
        if (checker.getRank() == Rank.SOLDIER)
            return cutDownSoldier(checker, delta);
        if (checker.getRank() == Rank.LADY)
            return cutDownLady(checker, delta);
        return Status.FAIL;
    }

    private Status cutDownLady(Checker checker, Vector delta) {
        if (isValidCutDown(checker, delta)) {
            var startVector = checker.getPosition();
            var enemyChecker = getCheckerOnWay(startVector, delta);
            this.locationCheckers.remove(enemyChecker);
            checker.move(delta);
            return Status.SUCCESS;
        }
        return Status.FAIL;
    }

    private Status cutDownSoldier(Checker checker, Vector delta) {
        var checkerVector = checker.getPosition();
        var vectorEnemyChecker = new Vector(checkerVector.getY() + delta.getY() / 2, checkerVector.getX() + delta.getX() / 2);
        var enemyChecker = getCheckerByCords(vectorEnemyChecker);

        if (isFreeCell(vectorEnemyChecker))
            return Status.FAIL;
        if (!isEnemyChecker(checker, enemyChecker))
            return Status.FAIL;

        locationCheckers.remove(enemyChecker);
        checker.move(delta);
        return Status.SUCCESS;
    }






















    private Status makeStepLady(Checker checker, Vector delta) {
        try {
            checker.move(delta);
        } catch (IllegalArgumentException e) {
            return Status.FAIL;
        }

        return Status.SUCCESS;
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

        if (isFreeCell(nextPosition))
            return countCheckersOnWay(checker, delta) == 1;

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
        var unitVector = createUnitVector(startVector, delta);
        var checkers = new ArrayList<Checker>();

        for (var i = 0; i < lengthWay; i++) {
            if (makeStepSoldier(pointer, unitVector) != Status.SUCCESS) {
                var enemyCheckerCords = pointer.getPosition().add(unitVector);
                checkers.add(getCheckerByCords(enemyCheckerCords));
                pointer.setPosition(enemyCheckerCords);
            }
        }
        return checkers;
    }

    private Vector createUnitVector(Vector startVector, Vector delta) {
        var length = Math.sqrt(delta.getX() * delta.getX() + delta.getY() * delta.getY());
        var x = (int) (delta.getX() / length * Math.sqrt(2));
        var y = (int) (delta.getY() / length * Math.sqrt(2));

        return new Vector(y, x);
    }

    private Status makeStepSoldier(Checker checker, Vector delta) {
        if (!isValidStepSoldier(checker, delta)) {
            return Status.FAIL;
        }
        var nextPosition = checker.getPosition().add(delta);

        if (isFreeCell(nextPosition)) {
            checker.move(delta);
            return Status.SUCCESS;
        }
        return Status.FAIL;
    }

    private boolean isValidStepSoldier(Checker checker, Vector delta) {
        if ((delta.getY() == 1 || delta.getY() == -1) && (delta.getX() == 1 || delta.getX() == -1)) {

            var nextPosition = checker.getPosition().add(delta);
            return isFreeCell(nextPosition);
        }

        return false;

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
