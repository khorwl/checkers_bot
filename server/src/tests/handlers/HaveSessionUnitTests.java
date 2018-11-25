package tests.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import core.userdb.UserDataBaseException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.http.HttpResponse;
import server.api.http.HttpStatusCode;
import server.api.http.HttpRequest;
import server.api.handlers.have_session.HaveSession;
import server.api.http.NoThatParameterException;
import server.api.response.Response;

public class HaveSessionUnitTests extends HandlerTestCase {

  private HaveSession handler;

  @BeforeEach
  public void init() {
    handler = new HaveSession(queryParser, server);
  }

  @Test
  public void handleRequest_withoutUserName_shouldNoThatParameterException() {
    var request = new HttpRequest("", Map.of("NAME", "MA<E"));

    assertThrows(NoThatParameterException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_noSuchUser_shouldThrowUserDataBaseException()
      throws UserDataBaseException {
    var request = new HttpRequest("", Map.of("name", "username"));
    when(userDataBase.getUser("username")).thenThrow(new UserDataBaseException());

    assertThrows(UserDataBaseException.class, () -> handler.handleRequest(request));
  }

  @Test
  public void handleRequest_noSessionForUser_shouldReturnFalse()
      throws UserDataBaseException, NoThatParameterException {
    var request = new HttpRequest("", Map.of("name", "user"));
    var expected = Response.createSuccess(false);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }

  @Test
  public void handleRequest_withSessionForThatUser_shouldReturnTrue()
      throws NoThatParameterException, UserDataBaseException {
    var request = new HttpRequest("", Map.of("name", "user"));
    var expected = Response.createSuccess(true);
    when(sessionServer.hasSessionWithUser(any())).thenReturn(true);

    var sut = handler.handleRequest(request);

    assertEquals(expected, sut);
  }
}
