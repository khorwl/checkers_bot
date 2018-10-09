package infra.quiz;

import java.io.IOException;
import java.nio.file.Path;

public class QuizLoader {

  private QuestionLoader loader;

  public QuizLoader() {
    loader = new QuestionLoader();
  }

  public Quiz loadFromDirectory(Path directoryPath) throws IOException {
    return new Quiz(loader.loadFromDirectory(directoryPath));
  }
}
