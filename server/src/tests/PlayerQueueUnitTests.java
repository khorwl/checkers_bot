package tests;

import core.queue.PlayerQueue;
import core.queue.PlayerQueueException;
import core.userdb.User;
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
  public void isEmpty_afterCreation_shouldBeTrue() {
    var q = queue();

    assertTrue(q.isEmpty());
  }

  @Test
  public void isEmpty_afterEnqueue_shouldBeFalse() {
    var q = queue();
    q.enqueue(new User("lol"));

    assertFalse(q.isEmpty());
  }

  @Test
  public void isEmpty_afterDequedPair_shouldBeTrue() {
    var q = queue();
    q.enqueue(new User("lol"));
    q.enqueue(new User("lol1"));
    q.dequeuePairOrNull();

    assertTrue(q.isEmpty());
  }

  @Test
  public void size_afterCreation_shouldBeZero() {
    assertEquals(queue().size(), 0);
  }

  @Test
  public void size_afterNenqueue_shouldBeN() {
    var q = queue();
    for (var i = 0; i < 42; i++) {
      q.enqueue(new User(Integer.toString(i)));
    }

    assertEquals(42, q.size());
  }

  @Test
  public void size_afterDequeue_shouldBeZero() {
    var q = queue();
    q.enqueue(new User("lol"));
    q.enqueue(new User("lol1"));
    q.dequeuePairOrNull();

    assertEquals(0, q.size());
  }

  @Test
  public void hasPair_lessThanTwoUsersPresentedInQueue_shouldBeFalse() {
    var q = queue();
    q.enqueue(new User("lol"));

    assertFalse(q.hasPair());
  }

  @Test
  public void hasPair_moreThanTwoUsersPresentedInQueue_shouldBeTrue() {
    var q = queue();
    q.enqueue(new User("lol"));
    q.enqueue(new User("lol1"));
    q.enqueue(new User("lol2"));

    assertTrue(q.hasPair());
  }

  @Test
  public void enqueue_uniqualUser_shouldReturnTrue() {
    var q = queue();

    var sut = q.enqueue(new User("lol"));

    assertTrue(sut);
  }

  @Test
  public void enqueue_userAreAlreadyInQueue_shouldReturnFalse() {
    var q = queue();
    q.enqueue(new User("lol"));

    var sut = q.enqueue(new User("lol"));

    assertFalse(sut);
  }

  @Test
  public void dequeuePair_ifNoPairInQueue_shouldThrowPlayerQueueException() {
    var q = queue();

    assertThrows(PlayerQueueException.class, q::dequeuePair);
  }

  @Test
  public void dequeuePair_ifPairExists_shouldReturnRightPair() throws PlayerQueueException {
    var q = queue();
    var user = new User("lol");
    q.enqueue(user);
    var user2 = new User("lol1");
    q.enqueue(user2);

    var pair = q.dequeuePair();

    if (!pair.equals(Pair.create(user, user2)) && !pair.equals(Pair.create(user2, user)))
      fail();
  }
}

