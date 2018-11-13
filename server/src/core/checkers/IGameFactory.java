package core.checkers;

import core.checkers.players.IPlayer;

public interface IGameFactory {
  IGame createGame(IPlayer whitePlayer, IPlayer blackPlayer);
}
