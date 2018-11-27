package tools.injection;

import com.google.inject.AbstractModule;
import core.CheckersServer;
import core.ICheckersServer;
import core.checkers.GameFactory;
import core.checkers.IGameFactory;
import core.queue.IPlayerQueue;
import core.queue.PlayerQueue;
import core.sessions.ISessionServer;
import core.sessions.SessionServer;
import core.userdb.IUserDataBase;
import core.userdb.UserDataBase;

public class BasicModule extends AbstractModule {
  @Override
  public void configure() {
    bind(IUserDataBase.class).to(UserDataBase.class);
    bind(IPlayerQueue.class).to(PlayerQueue.class);
    bind(ISessionServer.class).to(SessionServer.class);
    bind(IGameFactory.class).to(GameFactory.class);
    bind(ICheckersServer.class).to(CheckersServer.class);
  }
}
