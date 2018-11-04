package infra.sessions;

import java.util.HashMap;
import java.util.Map;
import tools.Pair;

public class PlayerQueue implements IPlayerQueue {
  private final Map<User, Integer> userToEnqueuedNumber;
  private int totalSize;

  public PlayerQueue() {
    userToEnqueuedNumber = new HashMap<>();
    totalSize = 0;
  }

  @Override
  public void enqueue(User user) {
    if (user == null)
      throw new IllegalArgumentException("User to add in userToEnqueuedNumber are null");

    var prevValue = userToEnqueuedNumber.getOrDefault(user, 0);

    userToEnqueuedNumber.put(user, prevValue + 1);
    totalSize++;
  }

  @Override
  public int getEnqueuedUsersAmount() {
    return userToEnqueuedNumber.size();
  }

  @Override
  public int getTotalSize() {
    return totalSize;
  }

  @Override
  public boolean hasPair() {
    return getEnqueuedUsersAmount() >= 2;
  }

  @Override
  public Pair<User, User> dequeuePair() {
    if (!hasPair())
      throw new IllegalStateException("No pair to dequeue");

    var it = userToEnqueuedNumber.keySet().iterator();
    var first = it.next();
    var second = it.next();
    userToEnqueuedNumber.put(first, userToEnqueuedNumber.get(first) - 1);
    userToEnqueuedNumber.put(second, userToEnqueuedNumber.get(second) - 1);
    totalSize -= 2;

    return Pair.create(first, second);
  }
}
