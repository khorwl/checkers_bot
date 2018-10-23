package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import infra.quiz.Question;
import infra.quiz.Quiz;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class QuizUnitTests {

  @Test
  public void getAllQuestions_returnsAllQuestionsFromCtor() {
    var questions = Arrays.asList(
        new Question("q1", "a1", "1"),
        new Question("q2", "a2", "2"),
        new Question("q2", "a3", "3"));
    var quiz = new Quiz(questions);

    assertEquals(quiz.getAllQuestions(), questions);
  }

  @Test
  public void getRandomQuestion_withoutParameter_shouldReturnQuestionFromAllQuestions() {
    var questions = Arrays.asList(
        new Question("q1", "a1", "1"),
        new Question("q2", "a2", "2"),
        new Question("q2", "a3", "3"));
    var quiz = new Quiz(questions);

    assertTrue(questions.contains(quiz.getRandomQuestion()));
  }

  @Test
  public void getNotProhibitedQuestions_shouldReturnAllQuestionsThanNotcontainedInProhibited() {
    var questions = Arrays.asList(
        new Question("q1", "a1", "1"),
        new Question("q2", "a2", "2"),
        new Question("q2", "a3", "3"));
    var quiz = new Quiz(questions);
    var prohibited = new HashSet<Question>();
    prohibited.add(new Question("q1", "a1", "1"));
    prohibited.add(new Question("q1", "a1", "3"));
    var expected = Collections.singletonList(new Question("q2", "a2", "2"));

    var sut = quiz.getNotProhibitedQuestions(prohibited);

    assertIterableEquals(sut, expected);
  }

  @Test
  public void haveNotProhibited_noAnyNotProhibitedQuestions_shouldReturnFalse() {
    var questions = Arrays.asList(
        new Question("q1", "a1", "1"),
        new Question("q2", "a2", "2"),
        new Question("q2", "a3", "3"));
    var quiz = new Quiz(questions);
    var prohibited = new HashSet<Question>();
    prohibited.add(new Question("q1", "a1", "1"));
    prohibited.add(new Question("q1", "a1", "3"));
    prohibited.add(new Question("q2", "a2", "2"));

    assertFalse(quiz.haveNotProhibited(prohibited));
  }

  @Test
  public void haveNotProhibited_haveNotProhibitedQuestions_shouldReturnTrue() {
    var questions = Arrays.asList(
        new Question("q1", "a1", "1"),
        new Question("q2", "a2", "2"),
        new Question("q2", "a3", "3"));
    var quiz = new Quiz(questions);
    var prohibited = new HashSet<Question>();
    prohibited.add(new Question("q1", "a1", "1"));
    prohibited.add(new Question("q2", "a2", "2"));

    assertTrue(quiz.haveNotProhibited(prohibited));
  }

  @Test
  public void getRandomQuestion_notAnyNotProhibitedQuestion_shouldThrowRuntimeError() {
    var questions = Arrays.asList(
        new Question("q1", "a1", "1"),
        new Question("q2", "a2", "2"),
        new Question("q2", "a3", "3"));
    var quiz = new Quiz(questions);
    var prohibited = new HashSet<Question>();
    prohibited.add(new Question("q1", "a1", "1"));
    prohibited.add(new Question("q1", "a1", "3"));
    prohibited.add(new Question("q2", "a2", "2"));

    assertThrows(RuntimeException.class, () -> quiz.getRandomQuestion(prohibited));
  }
}
