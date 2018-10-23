package infra.quiz;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Quiz {

  private List<Question> questions;
  private Random random;

  public Quiz(List<Question> questions) {
    this.questions = questions;
    random = new Random();
  }

  public List<Question> getAllQuestions() {
    return questions;
  }

  public Question getRandomQuestion() {
    return getRandomFrom(questions);
  }

  public Question getRandomQuestion(Set<Question> prohibited) {
    var notProhibited = getNotProhibitedQuestions(prohibited);

    if (notProhibited.size() == 0) {
      throw new RuntimeException("no questions to choose");
    }

    return getRandomFrom(notProhibited);
  }

  public boolean haveNotProhibited(Set<Question> prohibited) {
    var size = getNotProhibitedQuestions(prohibited).size();

    return size != 0;
  }

  public List<Question> getNotProhibitedQuestions(Set<Question> prohibited) {
    var notProhibited = new ArrayList<Question>();

    for (Question question : questions) {
      if (!prohibited.contains(question)) {
        notProhibited.add(question);
      }
    }

    return notProhibited;
  }

  public Question getQuestionById(String id) throws KeyException {
    int i = questions.indexOf(new Question("", "", id));
    if (i == -1) {
      throw new KeyException(String.format("No question with id %s", id));
    }

    return questions.get(i);
  }

  private Question getRandomFrom(List<Question> questions) {
    var idx = (random.nextInt() % questions.size() + questions.size()) % questions.size();

    return questions.get(idx);
  }
}
