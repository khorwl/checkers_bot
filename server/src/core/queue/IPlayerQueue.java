package core.queue;

import core.userdb.User;
import tools.Pair;

public interface IPlayerQueue {
  boolean enqueue(User user) throws PlayerQueueException;
  boolean hasPair();
  boolean isEmpty();
  int size();
  Pair<User, User> dequeuePair() throws PlayerQueueException;
  Pair<User, User> dequeuePairOrNull();
}
