package infra.sessions;

import java.security.KeyException;
import java.util.Set;

public interface IUserDataBase {
  boolean register(String name);
  boolean delete(String name);
  User getUser(String name) throws KeyException;
  Set<User> getUsers();
  boolean hasUser(String name);
}
