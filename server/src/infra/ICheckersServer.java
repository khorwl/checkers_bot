package infra;

import infra.checkers.primitives.Vector;
import infra.sessions.Session;
import infra.sessions.User;
import java.security.KeyException;
import java.util.Set;

public interface ICheckersServer {
  boolean registerUser(String name);
  boolean deleteUser(String name);
  Set<User> getUsers();

  void enqueueUserToPlayerQueue(String name) throws KeyException;

  boolean hasPairToStartSession();
  Session startSession();

  boolean makeTurn(String sessionId, Vector from, Vector to) throws KeyException;

  Session getSession(String sessionId) throws KeyException;
}
