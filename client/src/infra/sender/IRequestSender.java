package infra.sender;

public interface IRequestSender {
  Response sendRequest(Request request);
}
