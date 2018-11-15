package server.api.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseSerializer {

  private static final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

  public static String serializeToJson(Response response) {
    return gson.toJson(response);
  }
}
