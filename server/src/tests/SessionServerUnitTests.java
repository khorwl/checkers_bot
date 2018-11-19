package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import core.checkers.game.IGame;
import core.checkers.IGameFactory;
import core.sessions.Session;
import core.sessions.SessionServer;
import core.sessions.SessionServerException;
import core.userdb.User;
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
  public void getSessions_shouldReturnRightSessionList() throws SessionServerException {
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
  public void getSession_shouldReturnRightSession() throws SessionServerException {
    var s = server.createSession(white, black);

    var sut = server.getSession(s.getId());

    assertEquals(s, sut);
  }

  @Test
  public void getSession_withUnknownSessionId_shouldThrowSessionServerException() {
    assertThrows(SessionServerException.class, () -> server.getSession("my_id"));
  }

  @Test
  public void endSession_withUnknownSessionId_shouldThrowSessionServerException() {
    assertThrows(SessionServerException.class, () -> server.endSession("mu"));
  }

  @Test
  public void endSession_shouldDeleteSession() throws SessionServerException {
    var game = mock(IGame.class);
    when(game.getWhitePlayer()).thenReturn(white);
    when(game.getBlackPlayer()).thenReturn(black);
    when(gameFactory.createGame(any(), any())).thenReturn(game);
    var s = server.createSession(white, black);

    server.endSession(s.getId());

    assertEquals(0, server.getSessions().size());
  }

  @Test
  public void createSession_shouldCallGameFactory() throws SessionServerException {
    server.createSession(null, null);

    verify(gameFactory, times(1)).createGame(any(), any());
  }

  @Test
  public void createSession_shouldReturnSessionWithGameFromFactory() throws SessionServerException {
    var game = mock(IGame.class);
    when(gameFactory.createGame(any(), any())).thenReturn(game);

    var sut = server.createSession(null, null);

    assertEquals(game, sut.getGame());
  }

  @Test
  public void createSession_shouldReturnSessionsWithUniqualIds() throws SessionServerException {
    var ids = new HashSet<String>();
    var testNum = 100000;

    for (var i = 0; i < testNum; i++) {
      User whiteUser = new User(Integer.toString(2 * i + 1));
      User blackUser = new User(Integer.toString(2 * i + 2));
      ids.add(server.createSession(whiteUser, blackUser).getId());
    }

    assertEquals(testNum, ids.size());
  }

  @Test
  public void createSession_alreadyHaveSessionWithThatUser_shouldThrowSessionServerError()
      throws SessionServerException {
    server.createSession(white, black);

    assertThrows(SessionServerException.class, () -> server.createSession(white, null));
  }

  @Test
  public void createSession_withUserThatEndedPreviousSession_shouldReturnRightSession()
      throws SessionServerException {
    var game = mock(IGame.class);
    when(game.getWhitePlayer()).thenReturn(white);
    when(game.getBlackPlayer()).thenReturn(black);
    when(gameFactory.createGame(any(), any())).thenReturn(game);
    var s = server.createSession(white, black);
    server.endSession(s);

    assertDoesNotThrow(() -> server.createSession(white, black));
  }

  @Test
  public void hasSessionWithUser_ifNoSessionWithThatUser_shouldReturnFalse() {
    assertFalse(server.hasSessionWithUser(white));
  }

  @Test
  public void hasSessionWithUser_ifThereIsSessionWithThatUser_shouldReturnTrue()
      throws SessionServerException {
    server.createSession(white, black);

    assertTrue(server.hasSessionWithUser(black));
  }

  @Test
  public void getSessionWithUserOrNull_ifNoSessionWithThatUser_shouldReturnNull() {
    assertNull(server.getSessionWithUserElseNull(white));
  }

  @Test
  public void getSessionWithUserOrNull_ifThereIsSessionWithThatUser_shouldReturnRightSession()
      throws SessionServerException {
    var s = server.createSession(white, black);

    assertEquals(s, server.getSessionWithUserElseNull(black));
  }
}
