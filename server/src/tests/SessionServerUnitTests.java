package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import core.checkers.IGameFactory;
import core.sessions.Session;
import core.sessions.SessionServer;
import core.sessions.User;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionServerUnitTests {
  private SessionServer server;
  private IGameFactory gameFactory;
  private User white, black, whiteOther, blackOther;

  @BeforeEach
  public void init() {
    gameFactory = mock(IGameFactory.class);
    server = new SessionServer(gameFactory);

    white = new User("white");
    black = new User("black");
    whiteOther = new User("whiteOther");
    blackOther = new User("blackOther");
  }

  @Test
  public void createSession_shouldReturnSessionWithGivenUsers() {
    var sut = server.createSession(white, black);

    assertEquals(white, sut.getWhitePlayer());
    assertEquals(black, sut.getBlackPlayer());
  }

  @Test
  public void createSession_shouldReturnSessionsWithDifferentIds() {
    var idSet = new HashSet<>();

    for (var i = 0; i < 1000; i++)
    {
      idSet.add(server.createSession(white, black).getId());
    }

    assertEquals(1000, idSet.size());
  }

  @Test
  public void getSessions_shouldReturnRightSessionList() {
    var s1 = server.createSession(white, black);
    var s2 = server.createSession(whiteOther, blackOther);
    var expected_ = new ArrayList<Session>();
    expected_.add(s2);
    expected_.add(s1);
    var expected = new HashSet<>(expected_);

    var sut = new HashSet<>(server.getSessions());

    assertIterableEquals(expected, sut);
  }

  @Test
  public void getSession_shouldReturnRightSession() throws KeyException {
    var s = server.createSession(white, black);

    var sut = server.getSession(s.getId());

    assertEquals(s, sut);
  }

  @Test
  public void getSession_withUnknownSessionId_shouldThrowKeyException() {
    assertThrows(KeyException.class, () -> server.getSession("my_id"));
  }

  @Test
  public void endSession_withUnknownSessionId_shouldThrowKeyException() {
    assertThrows(KeyException.class, () -> server.endSession("mu"));
  }

  @Test
  public void endSession_shouldDeleteSession() throws KeyException {
    var s = server.createSession(white, black);

    server.endSession(s.getId());

    assertEquals(0, server.getSessions().size());
  }
}
