package core.checkers;

import core.checkers.game.IGame;
import core.checkers.players.IPlayer;

public interface IGameFactory {

  IGame createGame(IPlayer whitePlayer, IPlayer blackPlayer);
}
