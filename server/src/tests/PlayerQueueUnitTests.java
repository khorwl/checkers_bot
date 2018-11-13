package tests;

import core.sessions.PlayerQueue;
import core.sessions.User;
import org.junit.jupiter.api.*;
import tools.Pair;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerQueueUnitTests {

  private PlayerQueue queue() {
    return new PlayerQueue();
  }

  @Test
  public void enqueue_userAreNull_shouldThrowIllegalArgumentException() {
    var q = queue();

    assertThrows(IllegalArgumentException.class, () -> q.enqueue(null));
  }

  @Test
  public void getSize_atBeginningAreZero() {
    assertEquals(0, queue().getEnqueuedUsersAmount());
  }

  @Test
  public void getSize_afterNEnqueuesOfDifferentUsers_shouldBeN() {
    var q = queue();
    for (var i = 0; i < 43; i++) {
      q.enqueue(new User("lol" + Integer.toString(i)));
    }

    assertEquals(43, q.getEnqueuedUsersAmount());
  }

  @Test
  public void getSize_shouldBeNumberOfUniqualUsers() {
    var q = queue();
    for (var i = 0; i < 42; i++) {
      q.enqueue(new User("user" + Integer.toString(i % 3)));
    }

    assertEquals(3, q.getEnqueuedUsersAmount());
  }

  @Test
  public void hasPair_ifThereIsNoTwoDifferentUser_shouldReturnFalse() {
    var q = queue();
    q.enqueue(new User("lol"));
    q.enqueue(new User("lol"));
    q.enqueue(new User("lol"));

    assertFalse(q.hasPair());
  }

  @Test
  public void hasPair_ifThereIsTwoOrMoreDifferentUsers_shouldReturnTrue() {
    var q = queue();
    q.enqueue(new User("lol"));
    q.enqueue(new User("lol2"));
    q.enqueue(new User("lol3"));
    q.enqueue(new User("lol3"));
    q.enqueue(new User("lol3"));

    assertTrue(q.hasPair());
  }

  @Test
  public void dequeuePair_ifThereIsNoPair_shouldThrowIllegalStateException() {
    var q = queue();
    q.enqueue(new User("lol"));
    q.enqueue(new User("lol"));

    assertThrows(IllegalStateException.class, q::dequeuePair);
  }

  @Test
  public void dequeuePair_thereIsValidPair_shouldReturnRightPair() {
    var q = queue();
    q.enqueue(new User("user1"));
    q.enqueue(new User("user2"));

    var sut = q.dequeuePair();
    var c1 = sut.equals(Pair.create(new User("user1"), new User("user2")));
    var c2 = sut.equals(Pair.create(new User("user2"), new User("user1")));

    if (!c1 && !c2) {
      fail("dequeued pair must have one of two values");
    }
  }
}
