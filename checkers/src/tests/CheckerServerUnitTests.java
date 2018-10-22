package tests;

import infra.CheckerServer;
import org.junit.jupiter.api.Test;
import primitives.*;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CheckerServerUnitTests {
    @Test
    void cutDownSoliderValid() {
        var pinkChecker = new Checker(new Vector(1, 2), Color.PINK, Rank.SOLDIER);
        var yellowChecker = new Checker(new Vector(3, 4), Color.YELLOW, Rank.SOLDIER);
        var other = new Checker(new Vector(4, 5), Color.YELLOW, Rank.LADY);
        var locationCheckers = new ArrayList<Checker>();
        locationCheckers.add(other);
        locationCheckers.add(pinkChecker);
        locationCheckers.add(yellowChecker);

        var server = new CheckerServer(locationCheckers);
        var delta = new Vector(-2, -2);
        var hh = new Checker(new Vector(2, 3), Color.YELLOW, Rank.LADY);

        var status = server.cutDownSoldier(hh, delta);

        assertEquals(status, Status.SUCCESS);
    }

    @Test
    void cutDownSoliderOtherVector(){
        var pinkChecker = new Checker(new Vector(1,2), Color.PINK, Rank.SOLDIER);
        var yellowChecker = new Checker(new Vector(3, 4), Color.YELLOW, Rank.SOLDIER);
        var other = new Checker(new Vector(4, 5), Color.YELLOW, Rank.LADY);
        var locationCheckers = new ArrayList<Checker>();
        locationCheckers.add(other);
        locationCheckers.add(pinkChecker);
        locationCheckers.add(yellowChecker);

        var server =  new CheckerServer(locationCheckers);
        var delta = new Vector(2, 2);
        var hh = new Checker(new Vector(0, 1), Color.YELLOW, Rank.LADY);

        var status = server.cutDownSoldier(hh,delta);

        assertEquals(status,Status.SUCCESS);
    }

    @Test
    void cutDownSoliderOtherVector2(){
        var pinkChecker = new Checker(new Vector(1,2), Color.PINK, Rank.SOLDIER);
        var yellowChecker = new Checker(new Vector(3, 4), Color.YELLOW, Rank.SOLDIER);
        var other = new Checker(new Vector(4, 5), Color.YELLOW, Rank.LADY);
        var locationCheckers = new ArrayList<Checker>();
        locationCheckers.add(other);
        locationCheckers.add(pinkChecker);
        locationCheckers.add(yellowChecker);

        var server =  new CheckerServer(locationCheckers);
        var delta = new Vector(-2, 2);
        var hh = new Checker(new Vector(2, 1), Color.YELLOW, Rank.LADY);

        var status = server.cutDownSoldier(hh,delta);

        assertEquals(status,Status.SUCCESS);
    }

    @Test
    void cutDownSoliderOtherVector3(){
        var pinkChecker = new Checker(new Vector(1,2), Color.PINK, Rank.SOLDIER);
        var yellowChecker = new Checker(new Vector(3, 4), Color.YELLOW, Rank.SOLDIER);
        var other = new Checker(new Vector(4, 5), Color.YELLOW, Rank.LADY);
        var locationCheckers = new ArrayList<Checker>();
        locationCheckers.add(other);
        locationCheckers.add(pinkChecker);
        locationCheckers.add(yellowChecker);

        var server =  new CheckerServer(locationCheckers);
        var delta = new Vector(2, -2);
        var hh = new Checker(new Vector(0, 3), Color.YELLOW, Rank.LADY);

        var status = server.cutDownSoldier(hh,delta);

        assertEquals(status,Status.SUCCESS);
    }
}
