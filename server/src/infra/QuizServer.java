package infra;

import infra.quiz.Question;
import infra.quiz.Quiz;
import infra.quiz.QuizLoader;
import java.io.IOException;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuizServer implements IQuizServer {

  private Map<String, User> nameToUser;
  private Quiz quiz;

  public QuizServer() throws IOException {
    nameToUser = new HashMap<>();
    quiz = new QuizLoader().loadFromDirectory(Paths.QuestionsDir);
  }

  public QuizServer(Quiz quiz) {
    nameToUser = new HashMap<>();
    this.quiz = quiz;
  }


  @Override
  public Set<User> getUsers() {
    return new HashSet<>(nameToUser.values());
  }

  @Override
  public boolean registerUser(String name) {
    if (nameToUser.containsKey(name)) {
      return false;
    }

    nameToUser.put(name, new User(name));

    return true;
  }

  @Override
  public boolean deleteUser(String name) {
    if (nameToUser.containsKey(name)) {
      nameToUser.remove(name);

      return true;
    }

    return false;
  }

  @Override
  public boolean haveUser(String name) {
    return nameToUser.containsKey(name);
  }

  @Override
  public User getUser(String name) throws KeyException {
    if (!haveUser(name)) {
      throw new KeyException(String.format("No user with name %s", name));
    }

    return nameToUser.get(name);
  }

  @Override
  public List<Question> getAnsweredQuestions(String userName) throws KeyException {
    return new ArrayList<>(getUser(userName).getAnsweredQuestions());
  }

  @Override
  public boolean haveQuestions(String userName) throws KeyException {
    return quiz.haveNotProhibited(getUser(userName).getAnsweredQuestions());
  }

  @Override
  public Question getQuestion(String userName) throws KeyException {
    Set<Question> answeredQuestions = getUser(userName).getAnsweredQuestions();
    return quiz.getRandomQuestion(answeredQuestions);
  }

  @Override
  public boolean submitAnswer(String userName, String questionId, String answer)
      throws KeyException {
    var question = quiz.getQuestionById(questionId);
    var user = getUser(userName);

    if (answer.equalsIgnoreCase(question.getAnswer())) {
      user.addAnsweredQuestion(question);
      return true;
    }

    return false;
  }

  @Override
  public HashMap<String, List<String>> getStatistic() {
    var dict = new HashMap<String, List<String>>();

    for (var user : nameToUser.keySet()) {
      var list = new ArrayList<String>();

      for (var q : nameToUser.get(user).getAnsweredQuestions()) {
        list.add(q.getId());
      }

      dict.put(user, list);
    }

    return dict;
  }
}
