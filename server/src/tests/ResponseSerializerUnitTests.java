package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import server.api.response.Response;
import server.api.response.ResponseSerializer;

public class ResponseSerializerUnitTests {

  @Test
  public void serializeToJson_responseOfTypeInteger_shouldReturnRightJson() {
    var response = Response.createFail(5);
    var expected =
        "{\n"
      + "  \"code\": \"FAIL\",\n"
      + "  \"dataObject\": 5\n"
      + "}";

    var sut = ResponseSerializer.serializeToJson(response);

    assertEquals(expected, sut);
  }

  @Test
  public void serializeToJson_responseOfTypeList_shouldReturnRightJson() {
    var response = Response.createInternalError(List.of(5, 7, 14));
    var expected =
        "{\n"
      + "  \"code\": \"INTERNAL_SERVER_ERROR\",\n"
      + "  \"dataObject\": [\n"
      + "    5,\n"
      + "    7,\n"
      + "    14\n"
      + "  ]\n"
      + "}";

    var sut = ResponseSerializer.serializeToJson(response);

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
  }

  @Test
  public void serializeToJson_ofCompleteType_shouldReturnRightJson() {
    var response = Response.createInvalidRequest(new MyClass());
    var expected =
        "{\n"
        + "  \"code\": \"INVALID_REQUEST\",\n"
        + "  \"dataObject\": {\n"
        + "    \"Field1\": 1488,\n"
        + "    \"Field2\": 1337,\n"
        + "    \"Field3\": 228\n"
        + "  }\n"
        + "}";

    var sut = ResponseSerializer.serializeToJson(response);

    assertEquals(expected, sut);
  }

  @Test
  public void serializeToJson_null_shouldReturnRightJson() {
    var response = Response.createInternalError((Integer)null);
    var expected =
        "{\n"
      + "  \"code\": \"INTERNAL_SERVER_ERROR\",\n"
      + "  \"dataObject\": null\n"
      + "}";

    var sut = ResponseSerializer.serializeToJson(response);

    assertEquals(expected, sut);
  }
}
