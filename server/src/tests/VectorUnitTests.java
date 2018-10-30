package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

import checkers.primitives.Vector;

public class VectorUnitTests {

  @Test
  public void equals_comparingToNull_shouldReturnFalse() {
    var v1 = new Vector(1, 2, 0);

    var sut = v1.equals(null);

    assertFalse(sut);
  }

  @Test
  public void equals_comparingToNonVectorType_shouldReturnFalse() {
    var v = new Vector(1, 2, 0);

    var sut = v.equals(5);

    assertFalse(sut);
  }

  @Test
  public void equals_comparingToItSelf_shouldReturnTrue() {
    var v = new Vector(1, 2, 0);

    var sut = v.equals(v);

    assertTrue(sut);
  }

  @Test
  public void equals_comparingToDifferentVectorInX_shouldReturnFalse() {
    var v1 = new Vector(-1, 1, 9);
    var v2 = new Vector(1, 1, 9);

    var sut = v1.equals(v2);

    assertFalse(sut);
  }

  @Test
  public void equals_comparingToDifferentVectorInY_shouldReturnFalse() {
    var v1 = new Vector(1, 6, 7);
    var v2 = new Vector(1, -6, 7);

    var sut = v1.equals(v2);

    assertFalse(sut);
  }

  @Test
  public void equals_comparingToDifferentVectorInZ_shouldReturnFalse() {
    var v1 = new Vector(1, 6, 7);
    var v2 = new Vector(1, 6, 8);

    var sut = v1.equals(v2);

    assertFalse(sut);
  }

  @Test
  public void equals_comparingToSameVector_shouldReturnTrue() {
    var v1 = new Vector(1, 2, 3);
    var v2 = new Vector(1, 2, 3);

    var sut = v1.equals(v2);

    assertTrue(sut);
  }


  @Test
  public void create_shouldReturnRightVector() {
    var expected = new Vector(1, 2, 3);

    var sut = Vector.create(1, 2, 3);

    assertEquals(expected, sut);
  }

  @Test
  public void hashCode_returnsDifferentValueForDifferentVectors() {
    //stupid test
    //in general, non equals objects can have equals hash codes
    //but this is simple check in random cases that there is not too many collisions
    var count = 0;

    for (var i = 0; i < 10000; i++) {
      var v1 = Vector.createRandom(-1000, 1000, -1000, 1000, -1000, 1000);
      var v2 = Vector.createRandom(-1000, 1000, -1000, 1000, -1000, 1000);

      if (v1.hashCode() == v2.hashCode() && !v1.equals(v2)) {
        count++;
      }
    }

    assertTrue(count < 25);
  }

  @Test
  public void zero_shouldReturnZeroVector() {
    var expected = new Vector(0, 0, 0);

    assertEquals(Vector.zero, expected);
  }

  @Test
  public void xUnit_shouldBeRightVector() {
    var expected = new Vector(1, 0, 0);

    assertEquals(Vector.xUnit, expected);
  }

  @Test
  public void yUnit_shouldBeRightVector() {
    var expected = new Vector(0, 1, 0);

    assertEquals(Vector.yUnit, expected);
  }

  @Test
  public void zUnit_shouldBeRightVector() {
    var expected = new Vector(0, 0, 1);

    assertEquals(expected, Vector.zUnit);
  }

  @Test
  public void getX_shouldReturnRightValue() {
    var v = Vector.create(-1337, -1337, 0);

    assertEquals(-1337, v.getX());
  }

  @Test
  public void getY_shouldReturnRightValue() {
    var v = Vector.create(1337, -56, 123);

    assertEquals(-56, v.getY());
  }

  @Test
  public void getZ_shouldReturnRightValue() {
    var v = new Vector(133, -56, 123);

    assertEquals(123, v.getZ());
  }

  @Test
  public void add_shouldReturnRightVector() {
    var v1 = new Vector(1, 5, 9);
    var v2 = new Vector(-6, 10, 1);
    var expected = new Vector(-5, 15, 10);

    var sut = v1.add(v2);

    assertEquals(expected, sut);
  }

  @Test
  public void sub_shouldReturnRightValue() {
    var v1 = new Vector(1, 5, 6);
    var v2 = new Vector(7, 9, 3);
    var expected = new Vector(-6, -4, 3);

    var sut = v1.sub(v2);

    assertEquals(expected, sut);
  }

  @Test
  public void mul_shouldReturnRightValue() {
    var v1 = new Vector(5, -7, 8);
    var k = -4;
    var expected = new Vector(5 * -4, -7 * -4, 8 * -4);

    var sut = v1.mul(k);

    assertEquals(expected, sut);
  }

  @Test
  public void getManhattanLength_shouldReturnSumOfAbs() {
    var v = new Vector(5, -7, 2);

    var sut = v.getManhattanLength();

    assertEquals(5 + 7 + 2, sut);
  }

  @Test
  public void getChebushevLength_shouldReturnMaxOfAbs() {
    var v = new Vector(7, -9, 3);

    var sut = v.getChebushevLength();

    assertEquals(9, sut);
  }

  @Test
  public void getManhattanLength_shouldReturnRightValue() {
    var v1 = new Vector(5, -13, 5);
    var v2 = new Vector(10, 17, 1);
    var expected = 5 + 13 + 17 + 4;

    var sut1 = Vector.getManhattanLength(v1, v2);
    var sut2 = Vector.getManhattanLength(v2, v1);

    assertEquals(expected, sut1);
    assertEquals(expected, sut2);
  }

  @Test
  public void getChebushevLength_shouldReturnRightValue() {
    var v1 = new Vector(5, -13, 5);
    var v2 = new Vector(10, 17, 1);
    var expected = 13 + 17;

    var sut1 = Vector.getChebushevLength(v1, v2);
    var sut2 = Vector.getChebushevLength(v2, v1);

    assertEquals(expected, sut1);
    assertEquals(expected, sut2);
  }
}
