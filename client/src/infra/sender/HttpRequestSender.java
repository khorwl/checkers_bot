package infra.sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequestSender implements IRequestSender {

  private String baseUrl;

  public HttpRequestSender(String baseUrl)
  {
    this.baseUrl = baseUrl;
  }

  public Response sendRequest(Request request) {
    HttpURLConnection connection = null;
    int code = -1;
    String body = null;

    try {
      var urlString = baseUrl + request.getUrl() + getQueryString(request.getQueryParams());
      var url = new URL(urlString);
      connection = (HttpURLConnection) url.openConnection();
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);
      connection.setRequestMethod("GET");
      connection.setDoOutput(true);

      var bodyStreamWriter = new OutputStreamWriter(connection.getOutputStream());
      var bodyToSend = request.getBody() == null ? "" : request.getBody();
      bodyStreamWriter.write(bodyToSend);
      bodyStreamWriter.close();

      code = connection.getResponseCode();
      var br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      body = readAll(br);
      br.close();
    } catch (IOException e) {
      //empty, just do nothing
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }

    return new Response(code, body);
  }


  private static String getQueryString(Map<String, String> params) {
    var builder = new StringBuilder();

    if (params != null && params.size() != 0) {
      builder.append('?');

      for (var key : params.keySet()) {
        var value = params.get(key);
        builder.append(key).append('=').append(value).append('&');
      }
    }

    return builder.toString();
  }

  private static String readAll(BufferedReader reader) throws IOException {
    var builder = new StringBuilder();
    var line = reader.readLine();
    var sep = System.lineSeparator();

    while (line != null) {
      builder.append(line).append(sep);
      line = reader.readLine();
    }

    return builder.toString();
  }
}
