package core.checkers;

import core.checkers.primitives.Checker;
import core.checkers.primitives.Color;
import core.checkers.primitives.Rank;
import core.checkers.primitives.Vector;

import java.util.ArrayList;

public class Generator {

    public ArrayList<Checker> getStartCheckers() {
        var checkers = generateCheckersPlayer(Color.WHITE);
        checkers.addAll(generateCheckersPlayer(Color.BLACK));

        return checkers;
    }

    private ArrayList<Checker> generateCheckersPlayer(Color color) {
        var checkers = new ArrayList();

        var z = color == Color.WHITE ? 0 : 8;

        for (var i = 0; i < Constants.size; i++) {
            for (var j = 0; j < Constants.size; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0)
                        continue;
                    else
                        checkers.add(new Checker(new Vector(j, i, z), color, Rank.SOLDIER));
                } else {
                    if (j % 2 == 0)
                        checkers.add(new Checker(new Vector(j, i, z), color, Rank.SOLDIER));
                    else
                        continue;
                }
            }
        }

        return checkers;
    }
}
