package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import server.api.response.Response;
import server.api.response.ResponseDeserializer;

public class ResponseDeserializerUnitTests {

  @Test
  public void deserializeFromJson_withIntegerObject_shouldReturnRightResponse() {
    var json =
        "{\n"
      + "  \"code\": \"INTERNAL_SERVER_ERROR\",\n"
      + "  \"dataObject\": 5,\n"
      + "  \"message\": \"\""
      + "}";
    var expected = Response.createInternalError(5);

    var sut = ResponseDeserializer.deserializeFromJson(json, Integer.class);

    assertEquals(expected, sut);
  }

  class MyClass {
    public int Field1;
    public int Field2;
    private int Field3;

    public MyClass() {
      Field1 = 1488;
      Field2 = 1337;
      Field3 = 228;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null)
        return false;

      if (obj == this)
        return true;

      if (obj instanceof MyClass) {
        var other = (MyClass)obj;

        return Field1 == other.Field1 && Field2 == other.Field2 && Field3 == other.Field3;
      }

      return false;
    }
  }

  @Test
  public void deserializeFromJson_withObjectOfComplexType_shouldReturnRightResponse() {
    var json =
        "{\n"
      + "  \"code\": \"INVALID_REQUEST\",\n"
      + "  \"dataObject\": {\n"
      + "    \"Field1\": 1488,\n"
      + "    \"Field2\": 1337,\n"
      + "    \"Field3\": 228\n"
      + "  },\n"
      + "  \"message\": \"message\"\n"
      + "}";
    var expected = Response.createInvalidRequest("message", new MyClass());

    var sut = ResponseDeserializer.deserializeFromJson(json, MyClass.class);

    assertEquals(expected, sut);
  }
}
