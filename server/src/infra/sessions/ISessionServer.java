package infra.sessions;

import java.security.KeyException;
import java.util.List;

public interface ISessionServer {
  Session createSession(User whitePlayer, User blackPlayer);
  List<Session> getSessions();
  Session getSession(String sessionId) throws KeyException;
  void endSession(Session session) throws KeyException;
  void endSession(String sessionId) throws KeyException;
  boolean hasSession(String sessionId);
}
