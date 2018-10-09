package tests;

import infra.QuizServer;
import infra.User;
import infra.quiz.Question;
import infra.quiz.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class QuizServerUnitTests {

  private QuizServer quizServer;
  private Quiz quiz;

  @BeforeEach
  public void setUp() {
    var questions = new ArrayList<Question>();
    questions.add(new Question("q1", "a1", "1"));
    questions.add(new Question("q2", "a2", "2"));
    questions.add(new Question("q3", "a3", "3"));

    quiz = new Quiz(questions);

    quizServer = new QuizServer(quiz);
  }

  @Test
  public void getUsers_afterCreation_shouldReturnEmptyList() {
    assertEquals(0, quizServer.getUsers().size());
  }

  @Test
  public void registerUser_noSuchUser_shouldReturnTrue() {
    assertTrue(quizServer.registerUser("myname"));
  }

  @Test
  public void registerUser_userAlreadyExists_shouldReturnFalse() {
    quizServer.registerUser("myname");

    assertFalse(quizServer.registerUser("myname"));
  }

  @Test
  public void getUsers_afterRegisterUser_shouldReturnSetWithNewUser() {
    quizServer.registerUser("myname");

    assertTrue(quizServer.getUsers().contains(new User("myname")));
  }

  @Test
  public void deleteUser_userDoesntExist_shouldReturnFalse() {
    assertFalse(quizServer.deleteUser("myname"));
  }

  @Test
  public void deleteUser_userExists_shouldReturnTrue() {
    quizServer.registerUser("myname");

    assertTrue(quizServer.deleteUser("myname"));
  }

  @Test
  public void getUsers_afterDelete_shouldReturnSetWithoutUser() {
    quizServer.registerUser("myname");
    quizServer.deleteUser("myname");

    assertEquals(0, quizServer.getUsers().size());
  }

  @Test
  public void getUser_unexistingUser_shouldThrowKeyException() {
    assertThrows(KeyException.class, () -> quizServer.getUser("myname"));
  }

  @Test
  public void submitAnswer_noQuestionWithGivenId_shouldThrowKeyException() {
    assertThrows(KeyException.class, () -> quizServer.submitAnswer("myname", "no-id", "lol"));
  }

  @Test
  public void submitAnswer_noUser_shouldThrowKeyException() {
    assertThrows(KeyException.class, () -> quizServer.submitAnswer("lol", "1", "lol"));
  }

  @Test
  public void submitAnswer_incorrectAnswer_shouldReturnFalse() throws KeyException {
    quizServer.registerUser("my");

    assertFalse(quizServer.submitAnswer("my", "1", "a2"));
  }

  @Test
  public void submitAnswer_correctAnswer_shouldReturnTrue() throws KeyException {
    quizServer.registerUser("my");

    assertTrue(quizServer.submitAnswer("my", "1", "a1"));
  }

  @Test
  public void submitAnswer_correctAnswer_shouldAddQuestionToAnsweredOfUser() throws KeyException {
    quizServer.registerUser("my");
    quizServer.submitAnswer("my", "1", "a1");

    var sut = quizServer.getUser("my").getAnsweredQuestions().toArray();

    assertArrayEquals(sut, new Question[]{new Question("q1", "a1", "1")});
  }

  @Test
  public void submitAnswer_incorrectAnswer_shouldNotChangeAnsweredQuestions() throws KeyException {
    quizServer.registerUser("my");
    quizServer.submitAnswer("my", "1", "a2");

    var sut = quizServer.getUser("my").getAnsweredQuestions().toArray();

    assertEquals(0, sut.length);
  }
}
