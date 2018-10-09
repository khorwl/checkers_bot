package tools;

import java.util.HashMap;
import java.util.Map;

public class QueryParser {

  public Map<String, String> parseToMap(String query) {
    if (query == null || query.isEmpty()) {
      return new HashMap<>();
    }

    Map<String, String> result = new HashMap<>();
    for (String param : query.split("&")) {
      String[] entry = param.split("=");
      if (entry.length > 1) {
        result.put(entry[0], entry[1]);
      } else {
        result.put(entry[0], "");
      }
    }
    return result;
  }
}
