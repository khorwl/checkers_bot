package core.checkers;

import core.checkers.players.IPlayer;
import core.checkers.primitives.Color;
import core.checkers.primitives.TurnStatus;

public class Game implements IGame {
    private final IPlayer whitePlayer;
    private final IPlayer blackPlayer;
    private final GameState state;
    private final GameBoard board;

    public Game(IPlayer whitePlayer, IPlayer blackPlayer, GameState state, GameBoard board) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.state = state;
        this.board = board;
    }


    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public TurnStatus performNextTurn() {
        var player = state.getNextTurnOrder() == Color.WHITE ? whitePlayer : blackPlayer;

        if (!player.haveNextTurn())
            return TurnStatus.NO_TURN;

        var turn = player.getNextTurn();

        return board.makeMove(turn.from(), turn.to());
    }

    @Override
    public IPlayer getWhitePlayer() {
        return whitePlayer;
    }

    @Override
    public IPlayer getBlackPlayer() {
        return blackPlayer;
    }
}
