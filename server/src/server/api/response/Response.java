package server.api.response;

import server.api.http.HttpResponse;
import server.api.http.HttpStatusCode;

public class Response<T> {

  private final ResponseCode code;
  private final T dataObject;
  private final String message;

  public Response(ResponseCode code, String message, T dataObject) {
    this.code = code;
    this.dataObject = dataObject;
    this.message = message != null ? message : "";
  }

  public static <T> Response<T> create(ResponseCode code, T dataObject) {
    return new Response<>(code, "", dataObject);
  }

  public static <T> Response<T> createSuccess() {
    return create(ResponseCode.SUCCESS, null);
  }

  public static <T> Response<T> createFail() {
    return create(ResponseCode.FAIL, null);
  }

  public static <T> Response<T> createInternalError() {
    return create(ResponseCode.INTERNAL_SERVER_ERROR, null);
  }

  public static <T> Response<T> createInvalidRequest() {
    return create(ResponseCode.INVALID_REQUEST, null);
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

  public static <T> Response<T> create(ResponseCode code, String message, T dataObject) {
    return new Response<>(code, message, dataObject);
  }

  public static <T> Response<T> createSuccess(String message, T dataObject) {
    return create(ResponseCode.SUCCESS, message, dataObject);
  }

  public static <T> Response<T> createFail(String message, T dataObject) {
    return create(ResponseCode.FAIL, message, dataObject);
  }

  public static <T> Response<T> createInternalError(String message, T dataObject) {
    return create(ResponseCode.INTERNAL_SERVER_ERROR, message, dataObject);
  }

  public static <T> Response<T> createInvalidRequest(String message, T dataObject) {
    return create(ResponseCode.INVALID_REQUEST, message, dataObject);
  }

  public ResponseCode getCode() {
    return code;
  }

  public T getDataObject() {
    return dataObject;
  }

  public String getMessage() { return message; }

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

      if (dataObject == null)
        return other.dataObject == null;

      return code.equals(other.code) && message.equals(other.message) && dataObject.equals(other.dataObject);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return (message.hashCode() * 1033) ^ code.hashCode() ^ dataObject.hashCode();
  }

  @Override
  public String toString() {
    var dob = dataObject == null ? "null" : dataObject.toString();
    return String
        .format("Response %s with code: %s and object: %s", message, code.toString(), dob);
  }
}
