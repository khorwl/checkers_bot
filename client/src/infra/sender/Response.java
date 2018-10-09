package infra.sender;

public class Response {

  private String body;
  private int statusCode;

  public Response(int statusCode, String body) {
    this.body = body;
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getBody() {
    return body;
  }

  public boolean isSuccess() {
    return statusCode != -1;
  }
}
