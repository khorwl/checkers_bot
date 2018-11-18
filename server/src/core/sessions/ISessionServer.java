package core.sessions;

import core.userdb.User;
import java.security.KeyException;
import java.util.List;

public interface ISessionServer {
  Session createSession(User whiteUser, User blackUser) throws SessionServerException;
  Session createAISessionForWhite(User user) throws SessionServerException;
  Session createAISessionForBlack(User user) throws SessionServerException;
  List<Session> getSessions();
  Session getSession(String sessionId) throws SessionServerException;
  Session getSessionElseNull(String sessionId);
  Session getSessionWithUser(User user) throws SessionServerException;
  Session getSessionWithUserElseNull(User user);
  boolean hasSessionWithUser(User user);
  void endSession(Session session) throws SessionServerException;
  void endSession(String sessionId) throws SessionServerException;
  boolean hasSession(String sessionId);
}
