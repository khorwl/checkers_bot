package infra.checkers;

import infra.sessions.User;

public interface IGameFactory {
  IGame createGame(User whitePlayer, User blackPlayer);
}
