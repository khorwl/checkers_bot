package tools;

import com.google.gson.Gson;
import infra.quiz.Question;

public class QuestionDeserializer {

  private Gson gson;

  public QuestionDeserializer() {
    gson = new Gson();
  }


  public Question deserializeFromJson(String json) {
    return gson.fromJson(json, Question.class);
  }
}
