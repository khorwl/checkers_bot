package infra.quiz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import tools.QuestionDeserializer;

public class QuestionLoader {

  private QuestionDeserializer deserializer;

  public QuestionLoader() {
    deserializer = new QuestionDeserializer();
  }

  public List<Question> loadFromDirectory(Path path) throws IOException {
    var questions = new ArrayList<Question>();

    var files = path.toFile().listFiles();

    if (files == null) {
      return null;
    }

    for (var file : files) {
      if (!file.isDirectory()) {
        questions.add(loadFromFile(file.toPath()));
      }
    }

    return questions;
  }

  public Question loadFromFile(Path path) throws IOException {
    return loadFromJsonText(new String(Files.readAllBytes(path)));
  }

  public Question loadFromJsonText(String json) {
    var question = deserializer.deserializeFromJson(json);

    //erase all '-' from id
    if (question.getId().contains("-")) {
      question = new Question(
          question.getQuestion(),
          question.getAnswer(),
          question.getId().replace('-', ' '));
    }

    return question;
  }
}
