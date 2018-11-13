package core.checkers;

import core.checkers.players.IPlayer;
import core.checkers.primitives.Checker;

import java.util.ArrayList;

public class GameFactory implements IGameFactory {
    private final GameState state = new GameState();
    private final Generator generator = new Generator();
    private final ArrayList<Checker> checkers = generator.getStartCheckers();
    private final GameBoard board = new GameBoard(checkers);

    @Override
    public IGame createGame(IPlayer whitePlayer, IPlayer blackPlayer) {
        return new Game(whitePlayer, blackPlayer, state, board);
    }
}
