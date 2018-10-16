package commands;

import infra.Environment;
import infra.ICommand;
import infra.sender.IRequestSender;
import infra.sender.Request;
import infra.ui.IStringReader;
import infra.ui.IStringWriter;
import java.util.HashMap;

public class AskCommand implements ICommand {

  private IStringReader reader;
  private IStringWriter writer;
  private IRequestSender sender;

  public AskCommand(IStringReader reader, IStringWriter writer, IRequestSender sender) {
    this.reader = reader;
    this.writer = writer;
    this.sender = sender;
  }

  @Override
  public void execute(Environment environment) {
    var user = environment.getLoggedUserName();
    if (user == null) {
      writer.writeString("You should login before execute ask");
      return;
    }

    if (!haveQuestions(user)) {
      writer.writeString("Sorry, no questions left");
      return;
    }

    var question = getNextQuestion(user);

    if (question == null) {
      writer.writeString("Error getting next question");
      return;
    }

    writer.writeString(question.getQuestion());
    var answer = reader.readNextString();

    var submitAreSuccessfull = submit(user, question.getId(), answer.trim());

    if (submitAreSuccessfull)
    {
      writer.writeString("Correct!");
    }
    else
    {
      writer.writeString("Wrong!");
    }
  }

  @Override
  public String getName() {
    return "ask";
  }

  private boolean haveQuestions(String name) {
    var params = new HashMap<String, String>();
    params.put("name", name);
    var request = new Request("/have_questions", params, null);
    var response = sender.sendRequest(request);

    return response.getStatusCode() == 200 && response.getBody().trim().equalsIgnoreCase("true");
  }

  private boolean submit(String name, String id, String answer) {
    var params = new HashMap<String, String>();
    params.put("name", name);
    params.put("id", id);
    var request = new Request("/submit", params, answer);

    var response = sender.sendRequest(request);

    return response.getStatusCode() == 200 && response.getBody().trim().equalsIgnoreCase("true");

  }

  private Question getNextQuestion(String name) {
    var params = new HashMap<String, String>();
    params.put("name", name);
    var request = new Request("/get_question", params, null);
    var response = sender.sendRequest(request);

    if (response.getStatusCode() == 200) {
      return parseQuestion(response.getBody());
    }

    return null;
  }

  private Question parseQuestion(String string) {
    var tokens = string.split("-");

    if (tokens.length != 2) {
      return null;
    }

    return new Question(tokens[0], tokens[1]);
  }

  private class Question {

    private String question;
    private String id;

    private Question(String id, String question) {
      this.question = question;
      this.id = id;
    }

    public String getQuestion() {
      return question;
    }

    public String getId() {
      return id;
    }
  }
}
