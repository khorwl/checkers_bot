package server;

import java.security.KeyException;
import java.util.Collections;
import java.util.Map;

public class Request {

  private final Map<String, String> queryToValue;
  private final String body;

  public Request(String body, Map<String, String> queryToValue) {
    this.queryToValue = Collections.unmodifiableMap(queryToValue);
    this.body = body;
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

    if (obj instanceof Request) {
      var other = (Request) obj;

      return body.equals(other.body) && queryToValue.equals(other.queryToValue);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return body.hashCode() ^ queryToValue.hashCode();
  }
}
