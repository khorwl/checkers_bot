package server.api.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ResponseDeserializer {

  private static final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

  public static Response deserializeFromJson(String json, Class<?> classOfDataObjeect) {
    return gson.fromJson(json, getType(classOfDataObjeect));
  }

  private static Type getType(Class<?> parameter) {
    return new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
        return new Type[]{parameter};
      }

      @Override
      public Type getRawType() {
        return Response.class;
      }

      @Override
      public Type getOwnerType() {
        return null;
      }
    };
  }
}
