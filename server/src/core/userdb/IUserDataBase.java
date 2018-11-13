package core.userdb;

import java.util.Set;

public interface IUserDataBase {
  boolean register(String name);
  boolean delete(String name);
  User getUser(String name) throws UserDataBaseException;
  Set<User> getUsers();
  boolean hasUser(String name);
}
