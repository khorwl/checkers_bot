package core.queue;

import core.userdb.User;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import tools.Pair;

public class PlayerQueue implements IPlayerQueue {

  private final Queue<User> queue;
  private final Set<User> users;

  public PlayerQueue() {
    users = new HashSet<>();
    queue = new LinkedList<>();
  }

  @Override
  public boolean enqueue(User user) {
    if (user == null) {
      throw new IllegalArgumentException("attempt to enqueue null user");
    }

    if (users.add(user))
    {
      queue.add(user);
      return true;
    }

    return false;
  }

  @Override
  public boolean hasPair() {
    return queue.size() >= 2;
  }

  @Override
  public boolean isEmpty() {
    return queue.isEmpty();
  }

  @Override
  public int size() {
    return queue.size();
  }

  @Override
  public Pair<User, User> dequeuePair() throws PlayerQueueException {
    var pair = dequeuePairOrNull();

    if (pair == null) {
      throw new PlayerQueueException("No pair to dequeue");
    }

    return pair;
  }

  @Override
  public Pair<User, User> dequeuePairOrNull() {
    if (!hasPair()) {
      return null;
    }

    var first = queue.remove();
    var second = queue.remove();

    users.remove(first);
    users.remove(second);

    return Pair.create(first, second);
  }
}
