package tools;

import com.google.gson.Gson;
import infra.quiz.Question;

public class QuestionSerializer {

  private Gson gson;

  public QuestionSerializer() {
    gson = new Gson();
  }

  public String serializeToJson(Question question) {
    return gson.toJson(question);
  }
}
