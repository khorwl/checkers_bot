package tests;

import infra.quiz.Question;
import infra.quiz.QuestionLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionLoaderUnitTests {

  @Test
  public void loadFromJsonText_shouldReturnRightQuestion() {
    var loader = new QuestionLoader();
    var expected = new Question("question", "answer", "my_id");

    var sut = loader.loadFromJsonText("{ " +
        "\"question\": \"my question\", " +
        "\"answer\": \"my answer\", " +
        "\"id\": \"my_id\"}");

    assertEquals(sut, expected);
  }
}
