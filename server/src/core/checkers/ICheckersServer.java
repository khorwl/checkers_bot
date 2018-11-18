package core.checkers;

import core.queue.IPlayerQueue;
import core.sessions.ISessionServer;
import core.userdb.IUserDataBase;

public interface ICheckersServer {
  IUserDataBase userDataBase();
  IPlayerQueue playerQueue();
  ISessionServer sessionServer();
}
