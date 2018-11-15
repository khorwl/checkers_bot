package server.api.http;

import java.security.KeyException;
import java.util.Collections;
import java.util.Map;

public class HttpRequest {

  private final Map<String, String> queryToValue;
  private final String body;

  public HttpRequest(String body, Map<String, String> queryToValue) {
    this.queryToValue = Collections.unmodifiableMap(queryToValue);
    this.body = body == null ? "" : body;
  }

  public boolean hasParameter(String key) {
    return queryToValue.containsKey(key);
  }

  public String getParameter(String key) throws KeyException {
    if (!hasParameter(key)) {
      throw new KeyException("No that key");
    }

    return queryToValue.get(key);
  }

  public String getParameterOrNull(String key) {
    return queryToValue.get(key);
  }

  public String getBody() {
    return body;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this == obj) {
      return true;
    }

    if (obj instanceof HttpRequest) {
      var other = (HttpRequest) obj;

      return body.equals(other.body) && queryToValue.equals(other.queryToValue);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return body.hashCode() ^ queryToValue.hashCode();
  }
}
