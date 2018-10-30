package infra;

import infra.checkers.primitives.Vector;
import java.util.List;

public interface ISessionServer {
  Session createSession(User whitePlayer, User blackPlayer);
  List<Session> getSessions();
  Session getSessionById(String id);
}
