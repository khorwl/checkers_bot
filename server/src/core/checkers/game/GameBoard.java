package core.checkers.game;

import core.checkers.primitives.*;
import core.checkers.primitives.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard {

  private HashSet<Checker> checkers;


  public GameBoard(HashSet<Checker> locationCheckers) {
    this.checkers = locationCheckers;
  }

  public Set<Checker> getCheckers() {
    return checkers;
  }

  public Checker getCheckerAt(Vector cords) {
    return checkers.stream().filter(ch -> ch.getPosition().equals(cords)).findFirst().orElse(null);
  }

  public TurnStatus makeMove(Vector from, Vector to) {
    var delta = to.sub(from);

    if (invalidDelta(delta)) {
      return TurnStatus.FAIL;
    }

    var checker = getCheckerAt(from);

    if (checker == null) {
      return TurnStatus.FAIL;
    }

    return makeMove(checker, delta);
  }

  private TurnStatus makeMove(Checker checker, Vector delta) {

    if (isCutDown(checker.getPosition(), delta)) {
      return cutDown(checker, delta);
    }
    if (isStep(checker.getPosition(), delta)) {
      return makeStep(checker, delta);
    }
    return TurnStatus.FAIL;
  }

  private boolean invalidDelta(Vector delta) {
    var moduleD = delta.abs();
    var x = moduleD.getX();
    var y = moduleD.getY();
    var z = moduleD.getZ();

    return !((x == y || Math.min(x, y) == 0) && (x == z || Math.min(x, z) == 0) && (z == y
        || Math.min(z, y) == 0));
  }

  private TurnStatus cutDown(Checker checker, Vector delta) {
    if (isValidCutDown(checker, delta)) {
      var enemyChecker = getCheckersOnWay(checker.getPosition(), delta).get(0);
      checkers.remove(enemyChecker);
      moveChecker(checker, delta);
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

    if (!nextPosition.inBoard()) {
      return false;
    }

    var moduleD = delta.abs();
    if (checker.getRank() == Rank.SOLDIER && (
        Math.max(moduleD.getZ(), Math.max(moduleD.getY(), moduleD.getX())) != 2)) {
      return false;
    }

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
    return getRay(from, delta).stream().filter(this::haveCheckerIn).map(this::getCheckerAt)
        .collect(Collectors.toList());
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
    if (!isValidStep(checker, delta)) {
      return TurnStatus.FAIL;
    }

    moveChecker(checker, delta);
    return TurnStatus.SUCCESS;
  }

  private boolean isValidStep(Checker checker, Vector delta) {
    var nextPosition = checker.getPosition().add(delta);

    if (checker.getRank() == Rank.SOLDIER && (
        Math.max(delta.getX(), Math.max(delta.getY(), delta.getZ())) != 1)) {
      return false;
    }

    if (!nextPosition.inBoard()) {
      return false;
    }

    return isFreeCell(nextPosition);
  }

  private boolean isFreeCell(Vector cords) {
    return checkers.stream().noneMatch(ch -> ch.getPosition().equals(cords));
  }

  private boolean isEnemyChecker(Checker source, Checker destination) {
    return !source.getColor().equals(destination.getColor());
  }

  private boolean haveCheckerIn(Vector cords) {
    return checkers.stream().filter(ch -> ch.getPosition().equals(cords)).count() == 1;
  }

  private void moveChecker(Checker ch, Vector delta) {
    checkers.add(ch.move(delta));
    checkers.remove(ch);
  }
}
