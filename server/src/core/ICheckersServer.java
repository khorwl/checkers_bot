package core;

import core.checkers.primitives.Vector;
import core.sessions.Session;
import core.sessions.User;
import java.security.KeyException;
import java.util.Set;

public interface ICheckersServer {
  boolean registerUser(String name);
  boolean deleteUser(String name);
  Set<User> getUsers();
  boolean hasUser(String name);

  void enqueueUserToPlayerQueue(String name) throws KeyException;

  boolean hasPairToStartSession();
  Session startSession();

  boolean makeTurn(String sessionId, Vector from, Vector to) throws KeyException;

  Session getSession(String sessionId) throws KeyException;
}