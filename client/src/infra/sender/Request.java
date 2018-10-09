package infra.sender;

import java.util.Map;

public class Request {
  private String url;
  private String body;
  private Map<String, String> queryParams;

  public Request(String url, Map<String, String> queryParams, String body)
  {
    this.url = url;
    this.body = body;
    this.queryParams = queryParams;
  }

  public String getUrl() {
    return url;
  }

  public String getBody() {
    return body;
  }

  public Map<String, String> getQueryParams() {
    return queryParams;
  }
}
