package core.queue;

import core.userdb.User;
import java.util.HashSet;
import java.util.Set;
import tools.Pair;

public class PlayerQueue implements IPlayerQueue {
  private final Set<User> users;

  public PlayerQueue() {
    users = new HashSet<>();
  }

  @Override
  public boolean enqueue(User user) {
    if (user == null)
      throw new IllegalArgumentException("attempt to enqueue null user");

    return users.add(user);
  }

  @Override
  public boolean hasPair() {
    return users.size() >= 2;
  }

  @Override
  public boolean isEmpty() {
    return users.isEmpty();
  }

  @Override
  public int size() {
    return users.size();
  }

  @Override
  public Pair<User, User> dequeuePair() throws PlayerQueueException {
    var pair = dequeuePairOrNull();

    if (pair == null)
      throw new PlayerQueueException("No pair to dequeue");

    return pair;
  }

  @Override
  public Pair<User, User> dequeuePairOrNull() {
    if (!hasPair())
      return null;

    //its guaranteed that next() will work at least 2 times
    var it = users.iterator();
    var first = it.next();
    var second = it.next();

    users.remove(first);
    users.remove(second);

    return Pair.create(first, second);
  }
}
