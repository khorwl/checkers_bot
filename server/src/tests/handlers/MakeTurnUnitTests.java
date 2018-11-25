package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.checkers.game.IGame;
import core.checkers.players.IPlayer;
import core.checkers.primitives.Turn;
import core.checkers.primitives.TurnStatus;
import core.checkers.primitives.Vector;
import core.sessions.Session;
import core.sessions.SessionServerException;
import core.tools.CoreException;
import core.userdb.User;
import core.userdb.UserDataBaseException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.handlers.make_turn.MakeTurn;
import server.api.http.HttpRequest;
import server.api.http.NoThatParameterException;
import server.api.response.Response;
import server.api.response.ResponseCode;

public class MakeTurnUnitTests extends HandlerTestCase {

  private MakeTurn handler;
  private Vector from;
  private Vector to;
  private HttpRequest validRequest;
  private User user;
  private Session session;
  private IGame game;
  private Turn turnFromValidRequest;

  @BeforeEach
  public void init() {
    handler = new MakeTurn(queryParser, server);
    from = Vector.create(1, 2, 3);
    to = Vector.create(5, 6, 7);
    user = new User("roma");
    validRequest = new HttpRequest("", Map.of("name", user.getName(), "from", from.toString(), "to", to.toString()));
    turnFromValidRequest = new Turn(from, to);
    game = mock(IGame.class);
    when(game.getWhitePlayer()).thenReturn(user);
    when(game.getBlackPlayer()).thenReturn(mock(IPlayer.class));

    session = new Session("", game);
  }

  @Test
  public void handleRequest_withoutName_shouldThrowNoThatParameterException() {
    var request = new HttpRequest("", Map.of("nAme", "lol", "from", "lal", "to", "(1,2,3)"));

    assertThrows(NoThatParameterException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_withoutFrom_shouldThrowNoThatParameterException() {
    var request = new HttpRequest("",
        Map.of("name", "roma", "to", Vector.create(1, 2, 3).toString()));

    assertThrows(NoThatParameterException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_withoutTo_shouldReturnInvalidRequest() {
    var request = new HttpRequest("",
        Map.of("name", "roma", "from", Vector.create(1, 2, 3).toString()));

    assertThrows(NoThatParameterException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_withInvalidFrom_shouldReturnInvalidRequest()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var request = new HttpRequest("",
        Map.of("name", "roma", "from", "not_a_vector", "to", Vector.create(1, 23, 4).toString()));

    assertEquals(handler.handleRequest(request).getCode(), ResponseCode.INVALID_REQUEST);
  }

  @Test
  public void handleRequest_withInvalidTo_shouldReturnInvalidRequest()
      throws SessionServerException, UserDataBaseException, NoThatParameterException {
    var request = new HttpRequest("",
        Map.of("name", "roma", "to", "not_a_vector", "from", Vector.create(1, 23, 4).toString()));

    assertEquals(handler.handleRequest(request).getCode(), ResponseCode.INVALID_REQUEST);
  }

  @Test
  public void handleRequest_withUnExistingUser_shouldThrowUserDataBaseException()
      throws UserDataBaseException {
    when(userDataBase.getUser("roma")).thenThrow(new UserDataBaseException());

    assertThrows(UserDataBaseException.class, () -> handler.handleRequest(validRequest));
  }

  @Test
  public void handleRequest_withExistingUserWithoutSession_shouldThrowSessionServerException()
      throws SessionServerException, UserDataBaseException {
    when(userDataBase.getUser(user.getName())).thenReturn(user);
    when(sessionServer.getSessionWithUser(any())).thenThrow(new SessionServerException());

    assertThrows(SessionServerException.class, () -> handler.handleRequest(validRequest));
  }

  @Test
  public void handleRequest_shouldSetNextTurn()
      throws CoreException, NoThatParameterException {
    when(userDataBase.getUser(user.getName())).thenReturn(user);
    when(sessionServer.getSessionWithUser(any())).thenReturn(session);

    handler.handleRequest(validRequest);

    assertEquals(turnFromValidRequest, user.getNextTurn());
  }

  @Test
  public void handleRequest_shouldReturnNoTurnIfPerformNextTurnReturnsNoTurn()
      throws CoreException, NoThatParameterException {
    when(userDataBase.getUser(user.getName())).thenReturn(user);
    when(sessionServer.getSessionWithUser(any())).thenReturn(session);
    when(game.performNextTurn()).thenReturn(TurnStatus.NO_TURN);
    var expected = Response.createSuccess(TurnStatus.NO_TURN);

    var sut = handler.handleRequest(validRequest);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_shouldReturnSuccessIfPerformNextTurnReturnsSuccess()
      throws CoreException, NoThatParameterException {
    when(userDataBase.getUser(user.getName())).thenReturn(user);
    when(sessionServer.getSessionWithUser(any())).thenReturn(session);
    when(game.performNextTurn()).thenReturn(TurnStatus.SUCCESS);
    var expected = Response.createSuccess(TurnStatus.SUCCESS);

    var sut = handler.handleRequest(validRequest);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_shouldReturnFailIfPerformNextTurnReturnsFail()
      throws CoreException, NoThatParameterException {
    when(userDataBase.getUser(user.getName())).thenReturn(user);
    when(sessionServer.getSessionWithUser(any())).thenReturn(session);
    when(game.performNextTurn()).thenReturn(TurnStatus.FAIL);
    var expected = Response.createSuccess(TurnStatus.FAIL);

    var sut = handler.handleRequest(validRequest);

    assertEquals(expected, sut);
  }
}
