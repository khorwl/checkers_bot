package server.api.response;

import server.api.http.HttpResponse;
import server.api.http.HttpStatusCode;

public class Response<T> {

  private final ResponseCode code;
  private final T dataObject;

  public Response(ResponseCode code, T dataObject) {
    this.code = code;
    this.dataObject = dataObject;
  }

  public static <T> Response<T> create(ResponseCode code, T dataObject) {
    return new Response<>(code, dataObject);
  }

  public static <T> Response<T> createSuccess(T dataObject) {
    return create(ResponseCode.SUCCESS, dataObject);
  }

  public static <T> Response<T> createFail(T dataObject) {
    return create(ResponseCode.FAIL, dataObject);
  }

  public static <T> Response<T> createInternalError(T dataObject) {
    return create(ResponseCode.INTERNAL_SERVER_ERROR, dataObject);
  }

  public static <T> Response<T> createInvalidRequest(T dataObject) {
    return create(ResponseCode.INVALID_REQUEST, dataObject);
  }

  public ResponseCode getCode() {
    return code;
  }

  public T getDataObject() {
    return dataObject;
  }

  public HttpResponse toHttpResponse() {
    return toHttpResponse(HttpStatusCode.OK);
  }

  public HttpResponse toHttpResponse(int code) {
    var body = ResponseSerializer.serializeToJson(this);

    return new HttpResponse(body, code);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this == obj) {
      return true;
    }

    if (obj instanceof Response) {
      var other = (Response) obj;

      return code.equals(other.code) && dataObject.equals(other.dataObject);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return code.hashCode() ^ dataObject.hashCode();
  }

  @Override
  public String toString() {
    return String
        .format("Response with code: %s and object: %s", code.toString(), dataObject.toString());
  }
}
