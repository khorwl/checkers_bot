package core.checkers;

import core.checkers.primitives.Checker;
import core.checkers.primitives.Color;
import core.checkers.primitives.Rank;
import core.checkers.primitives.Vector;

import java.util.HashSet;

public class Generator {

    public HashSet<Checker> getStartCheckers() {
        var checkers = generateCheckersPlayer(Color.WHITE);
        checkers.addAll(generateCheckersPlayer(Color.BLACK));

        return checkers;
    }

    private HashSet<Checker> generateCheckersPlayer(Color color) {
        var checkers = new HashSet();

        var z = color == Color.WHITE ? 0 : 7;

        for (var i = 0; i < Constants.BoardSize; i++) {
            for (var j = 0; j < Constants.BoardSize; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        if (color == Color.WHITE)
                            continue;
                        checkers.add(new Checker(new Vector(j, i, z), Color.BLACK, Rank.SOLDIER));
                    } else {
                        if (color == Color.BLACK)
                            continue;
                        checkers.add(new Checker(new Vector(j, i, z), Color.WHITE, Rank.SOLDIER));
                    }
                } else {
                    if (j % 2 == 0) {
                        if (color == Color.BLACK)
                            continue;
                        checkers.add(new Checker(new Vector(j, i, z), Color.WHITE, Rank.SOLDIER));
                    } else {
                        if (color == Color.WHITE)
                            continue;
                        checkers.add(new Checker(new Vector(j, i, z), Color.BLACK, Rank.SOLDIER));
                    }
                }
            }
        }

        return checkers;
    }
}
