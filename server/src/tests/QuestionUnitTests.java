package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import infra.quiz.Question;
import org.junit.jupiter.api.Test;

public class QuestionUnitTests {

  @Test
  public void getQuestion_shouldReturnQuestionFromCtor() {
    var question = new Question("this is question", "", "111");

    assertEquals(question.getQuestion(), "this is question");
  }

  @Test
  public void getAnswer_shouldReturnAnswerFromCtor() {
    var question = new Question("", "answer", "111");

    assertEquals(question.getAnswer(), "answer");
  }

  @Test
  public void getId_shouldReturnsIdFromCtor() {
    var question = new Question("", "", "id_lol");

    assertEquals(question.getId(), "id_lol");
  }

  @Test
  public void hashCode_shouldReturnHashCodeOfId() {
    var question = new Question("", "", "my id");

    assertEquals(question.hashCode(), question.getId().hashCode());
  }

  @Test
  public void equals_comparingWithNonQuestionType_shouldReturnFalse() {
    var question = new Question("", "", "");

    assertFalse(question.equals(5));
  }

  @Test
  public void equals_comparingToItself_shouldReturnTrue() {
    var question = new Question("", "", "");

    assertTrue(question.equals(question));
  }

  @Test
  public void equals_comparingToQuestionWithDifferentId_shouldReturnFalse() {
    var question = new Question("", "", "1");
    var other = new Question("", "", "2");

    assertNotEquals(question, other);
  }

  @Test
  public void equals_comparingToQuestionWithSameIdButDifferentQuestion_shouldReturnTrue() {
    var question = new Question("q1", "a2", "1");
    var other = new Question("q2", "a2", "1");

    assertEquals(question, other);
  }
}
