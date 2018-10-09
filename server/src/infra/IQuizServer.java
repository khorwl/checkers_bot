package infra;

import infra.quiz.Question;

import java.security.KeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IQuizServer {

  Set<User> getUsers();

  boolean registerUser(String name);

  boolean deleteUser(String name);

  boolean haveUser(String name);

  User getUser(String name) throws KeyException;

  List<Question> getAnsweredQuestions(String userName) throws KeyException;

  boolean haveQuestions(String userName) throws KeyException;

  Question getQuestion(String userName) throws KeyException;

  boolean submitAnswer(String userName, String questionId, String answer) throws KeyException;

  HashMap<String, List<String>> getStatistic();
}
