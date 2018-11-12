package infra.checkers;

import infra.checkers.primitives.*;

import java.util.ArrayList;

public class GameBoard implements IGameBoard {

    private ArrayList<Checker> checkers;


    public CheckerServer(ArrayList<Checker> locationCheckers) {
        this.checkers = locationCheckers;
    }

    @Override
    public ArrayList getCheckers() {
        return checkers;
    }

    @Override
    public Checker getCheckerAt(Vector cords) {
        for (Checker locationChecker : checkers) {
            if (locationChecker.getPosition().equals(cords))
                return locationChecker;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public TurnStatus makeMove(Checker checker, Vector delta) {
        if (Math.abs(delta.getY()) != Math.abs(delta.getX()))
            return TurnStatus.FAIL;
        if (isCutDown(checker, delta))
            return cutDown(checker, delta);
        if (isStep(checker, delta))
            return makeStep(checker, delta);
        return TurnStatus.FAIL;
    }

    @Override
    public TurnStatus makeMove(Vector from, Vector to) {
        var checker = getCheckerAt(from);
        var delta = to.sub(from);
        return makeMove(checker, delta);
    }

    private TurnStatus cutDown(Checker checker, Vector delta) {
        if (isValidCutDown(checker, delta)) {
            var enemyChecker = getCheckerOnWay(checker.getPosition(), delta);
            checkers.remove(enemyChecker);
            checker.move(delta);
            return TurnStatus.SUCCESS;
        }
        return TurnStatus.FAIL;
    }

    private boolean isCutDown(Checker checker, Vector delta) {
        return countCheckersOnWay(checker, delta) == 1;
    }

    private boolean isStep(Checker checker, Vector delta) {
        return isFreeWay(checker, delta);
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
            if (makeStep(pointer, unitVector) != TurnStatus.SUCCESS) {
                var enemyCheckerCords = pointer.getPosition().add(unitVector);
                checkers.add(getCheckerAt(enemyCheckerCords));
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
        if (!isValidStep(checker, delta))
            return Status.FAIL;

        checker.move(delta);
        return Status.SUCCESS;
    }

    private boolean isValidStep(Checker checker, Vector delta) {
        var nextPosition = checker.getPosition().add(delta);

        if (checker.getRank() == Rank.SOLDIER && Math.abs(delta.getY()) != 1)
            return false;

        if (nextPosition.getY() < 0 || nextPosition.getX() < 0)
            return false;

        return isFreeCell(nextPosition);
    }

    private boolean isFreeCell(Vector cords) {
        for (Checker locationChecker : checkers) {
            if (locationChecker.getPosition().equals(cords))
                return false;
        }
        return true;
    }

    private boolean isEnemyChecker(Checker source, Checker destination) {
        return !source.getColor().equals(destination.getColor());
    }


}
