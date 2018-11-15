package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import server.api.response.Response;
import server.api.response.ResponseCode;
import server.api.http.HttpStatusCode;

public class ResponseUnitTests {

  @Test
  public void equals_comparingWithNull_shouldReturnFalse() {
    assertNotEquals(Response.create(ResponseCode.FAIL, 5), null);
  }

  @Test
  public void equals_comparingToDifferentType_shouldReturnFalse() {
    assertNotEquals(Response.createSuccess(5), 5);
  }

  @Test
  public void equals_comparingToItself_shouldReturnTrue() {
    var r = Response.createSuccess(5);

    assertEquals(r, r);
  }

  @Test
  public void equals_comparingWithDifferentCodeButEqualsObjects_shouldReturnFalse() {
    var r1 = Response.createSuccess(5);
    var r2 = Response.createFail(5);

    assertNotEquals(r1, r2);
  }

  @Test
  public void equals_comparingWithDifferentObjectButEqualsCodes_shouldReturnFalse() {
    var r1 = Response.createFail(6);
    var r2 = Response.createFail(5);

    assertNotEquals(r1, r2);
  }

  @Test
  public void equals_comparingEqualResponses_shouldReturnTrue() {
    var r1 = Response.createSuccess(5);
    var r2 = Response.createSuccess(5);

    assertEquals(r1, r2);
  }

  @Test
  public void toHttpResponse_shouldReturnHttpResponseWithOKCode() {
    var r = Response.createFail(5);

    assertEquals(HttpStatusCode.OK, r.toHttpResponse().getStatusCode());
  }


}
