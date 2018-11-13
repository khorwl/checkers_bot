package core.sessions;

import tools.Pair;

public interface IPlayerQueue {
  void enqueue(User user);
  int getEnqueuedUsersAmount();
  int getTotalSize();
  boolean hasPair();
  Pair<User, User> dequeuePair();
}
