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
    void count_checherByWay(){
        var pinkChecker = new Checker(new Vector(1,2), Color.PINK, Rank.SOLDIER);
        var yellowChecker = new Checker(new Vector(3, 4), Color.YELLOW, Rank.SOLDIER);
        var other = new Checker(new Vector(4, 5), Color.YELLOW, Rank.LADY);
        var locationCheckers = new ArrayList<Checker>();
        locationCheckers.add(other);
        locationCheckers.add(pinkChecker);
        locationCheckers.add(yellowChecker);

        var server =  new CheckerServer(locationCheckers);
        var exception = 3;
        var hh = new Checker(new Vector(2, 3), Color.YELLOW, Rank.LADY);


    }

}
