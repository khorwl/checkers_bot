package infra.quiz;

public class Question {

  private String question;
  private String answer;
  private String id;

  public Question(String question, String answer, String id) {
    this.question = question;
    this.answer = answer;
    this.id = id;
  }

  public String getQuestion() {
    return question;
  }

  public String getAnswer() {
    return answer;
  }

  public String getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    return id.equals(((Question) obj).id);
  }
}
