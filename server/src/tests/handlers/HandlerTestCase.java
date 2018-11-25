package tests.handlers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.ICheckersServer;
import core.queue.IPlayerQueue;
import core.sessions.ISessionServer;
import core.userdb.IUserDataBase;
import org.junit.jupiter.api.BeforeEach;
import tools.QueryParser;

public class HandlerTestCase {
  protected IPlayerQueue playerQueue;
  protected ISessionServer sessionServer;
  protected IUserDataBase userDataBase;
  protected ICheckersServer server;
  protected QueryParser queryParser;

  @BeforeEach
  private void init_() {
    queryParser = new QueryParser();
    playerQueue = mock(IPlayerQueue.class);
    sessionServer = mock(ISessionServer.class);
    userDataBase = mock(IUserDataBase.class);

    server = mock(ICheckersServer.class);

    when(server.playerQueue()).thenReturn(playerQueue);
    when(server.sessionServer()).thenReturn(sessionServer);
    when(server.userDataBase()).thenReturn(userDataBase);
  }
}
