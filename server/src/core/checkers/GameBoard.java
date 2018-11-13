package core.checkers;

import core.checkers.primitives.*;
import core.checkers.primitives.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard {
    private ArrayList<Checker> checkers;


    public GameBoard(ArrayList<Checker> locationCheckers) {
        this.checkers = locationCheckers;
    }

    public ArrayList getCheckers() {
        return checkers;
    }

    private Checker getCheckerAt(Vector cords) {
        return checkers.stream().filter(ch -> ch.getPosition().equals(cords)).findFirst().orElseThrow();
    }

    public TurnStatus makeMove(Vector from, Vector to) {
        var checker = getCheckerAt(from);
        var delta = to.sub(from);
        return makeMove(checker, delta);
    }

    private TurnStatus makeMove(Checker checker, Vector delta) {
        if (Math.abs(delta.getY()) != Math.abs(delta.getX()) && Math.abs(delta.getY()) != Math.abs(delta.getZ()))
            return TurnStatus.FAIL;
        if (isCutDown(checker.getPosition(), delta))
            return cutDown(checker, delta);
        if (isStep(checker.getPosition(), delta))
            return makeStep(checker, delta);
        return TurnStatus.FAIL;
    }

    private TurnStatus cutDown(Checker checker, Vector delta) {
        if (isValidCutDown(checker, delta)) {
            var enemyChecker = getCheckersOnWay(checker.getPosition(), delta);
            checkers.remove(enemyChecker);
            checker.move(delta);
            return TurnStatus.SUCCESS;
        }
        return TurnStatus.FAIL;
    }

    private boolean isCutDown(Vector from, Vector delta) {
        return countCheckersOnWay(from, delta) == 1;
    }

    private boolean isStep(Vector from, Vector delta) {
        return isFreeWay(from, delta);
    }

    private boolean isValidCutDown(Checker checker, Vector delta) {
        var nextPosition = checker.getPosition().add(delta);

        if (checker.getRank() == Rank.SOLDIER && ((Math.abs(delta.getY()) != 2)))
            return false;

        if (isFreeCell(nextPosition)) {
            var checkers = getCheckersOnWay(checker.getPosition(), delta);
            return checkers.size() == 1 && isEnemyChecker(checkers.get(0), checker);
        }
        return false;
    }

    private boolean isFreeWay(Vector from, Vector delta) {
        return countCheckersOnWay(from, delta) == 0;
    }

    private int countCheckersOnWay(Vector from, Vector delta) {
        return getCheckersOnWay(from, delta).size();
    }

    private List<Checker> getCheckersOnWay(Vector from, Vector delta) {
        return getRay(from, delta).stream().filter(v -> haveCheckerIn(v)).map(v -> getCheckerAt(v)).collect(Collectors.toList());
    }

    private List<Vector> getRay(Vector from, Vector delta) {
        var ray = new ArrayList<Vector>();
        var unit = delta.getUnit();
        var finish = from.add(delta);

        while (!from.equals(finish)) {
            from = from.add(unit);
            ray.add(from);
        }
        return ray;
    }


    private TurnStatus makeStep(Checker checker, Vector delta) {
        if (!isValidStep(checker, delta))
            return TurnStatus.FAIL;

        checker.move(delta);
        return TurnStatus.SUCCESS;
    }

    private boolean isValidStep(Checker checker, Vector delta) {
        var nextPosition = checker.getPosition().add(delta);

        if (checker.getRank() == Rank.SOLDIER && Math.abs(delta.getY()) != 1)
            return false;

        if (!nextPosition.inBoard())
            return false;

        return isFreeCell(nextPosition);
    }

    private boolean isFreeCell(Vector cords) {
        return checkers.stream().filter(ch -> ch.getPosition().equals(cords)).count() == 0;
    }

    private boolean isEnemyChecker(Checker source, Checker destination) {
        return !source.getColor().equals(destination.getColor());
    }

    private boolean haveCheckerIn(Vector cords) {
        return checkers.stream().filter(ch -> ch.getPosition().equals(cords)).count() == 1;
    }
}
