package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tools.Pair;

public class PairUnitTests {
  @Test
  public void equals_comparingWithNull_shouldBeFalse() {
    var p = Pair.create("lol", "mde");

    var sut = p.equals(null);

    assertFalse(sut);
  }

  @Test
  public void equals_comparingWithNonPairType_shouldReturnFalse() {
    var p = Pair.create("lol", "mde");

    var sut = p.equals(5);

    assertFalse(sut);
  }

  class MySomeClassBecauseMockitoCantMockEqualsAndHashCode {
    private int equalsCalledTimes = 0;

    public int getNumberOfEqualsCalls() {
      return equalsCalledTimes;
    }

    @Override
    public boolean equals(Object obj) {
      equalsCalledTimes++;
      return false;
    }
  }

  @Test
  public void equals_comparingWithPairWithOtherGenericType_shouldNotCallEqualsOfPairElements() {
    var obj = new MySomeClassBecauseMockitoCantMockEqualsAndHashCode();
    var p1 = Pair.create(obj, "str");
    var p2 = Pair.create(5, "str");

    p1.equals(p2);

    assertEquals(0, obj.getNumberOfEqualsCalls(), "Number of equals calls != 0");
  }

  @Test
  public void equals_comparingWithSamePair_shouldReturnTrue() {
    var p1 = Pair.create("str", 5);
    var p2 = Pair.create("str", 5);

    assertEquals(p1, p2);
  }
}
