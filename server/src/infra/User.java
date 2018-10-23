package infra;

import infra.quiz.Question;
import java.util.HashSet;
import java.util.Set;

public class User {

  private String name;
  private HashSet<Question> answeredQuestions;

  public User(String name) {
    this.name = name;
    answeredQuestions = new HashSet<>();
  }

  public String getName() {
    return name;
  }

  public Set<Question> getAnsweredQuestions() {
    return answeredQuestions;
  }

  public void addAnsweredQuestion(Question question) {
    answeredQuestions.add(question);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj == this) {
      return true;
    }

    if (obj.getClass() != getClass()) {
      return false;
    }

    return name.equalsIgnoreCase(((User) obj).name);
  }
}