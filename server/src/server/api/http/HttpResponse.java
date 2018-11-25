package server.api.http;

public class HttpResponse {
  private final String body;
  private final int statusCode;

  public HttpResponse(String body, int statusCode) {
    this.body = body;
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getBody() {
    return body;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;

    if (obj == this)
      return true;

    if (obj instanceof HttpResponse) {
      var other = (HttpResponse)obj;

      return body.equals(other.body) && statusCode == other.statusCode;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return body.hashCode() ^ Integer.hashCode(statusCode);
  }

  @Override
  public String toString() {
    var body = this.body.length() < 50 ? this.body : this.body.substring(0, 47) + "...";

    return String.format("HttpResponse: %d %s", statusCode, body);
  }
}
