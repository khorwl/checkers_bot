package core.sessions;

import core.userdb.User;
import java.security.KeyException;
import java.util.List;

public interface ISessionServer {
  Session createSession(User whiteUser, User blackUser);
  Session createAISessionForWhite(User user);
  Session createAISessionForBlack(User user);
  List<Session> getSessions();
  Session getSession(String sessionId) throws KeyException;
  void endSession(Session session) throws KeyException;
  void endSession(String sessionId) throws KeyException;
  boolean hasSession(String sessionId);
}
