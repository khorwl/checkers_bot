package core.sessions;

import java.security.KeyException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserDataBase implements IUserDataBase {
  private final Map<String, User> nameToUser;

  public UserDataBase() {
    nameToUser = new HashMap<>();
  }

  @Override
  public boolean register(String name) {
    if (hasUser(name))
      return false;

    nameToUser.put(name, new User(name));

    return true;
  }

  @Override
  public boolean delete(String name) {
    if (!hasUser(name))
      return false;

    nameToUser.remove(name);

    return true;
  }

  @Override
  public User getUser(String name) throws KeyException {
    if (!hasUser(name))
      throw new KeyException(String.format("No such user: %s", name));

    return nameToUser.get(name);
  }

  @Override
  public Set<User> getUsers() {
    return new HashSet<>(nameToUser.values());
  }

  @Override
  public boolean hasUser(String name) {
    return nameToUser.containsKey(name);
  }
}
