package com.silvanschermer.http;

public class HttpRequest {
  String rawHeaders;
  String[] headers;

  public HttpRequest(String rawHeaders, String[] headers) {
    this.rawHeaders = rawHeaders;
    this.headers = headers;
  }

  public String getRawHeaders() {
    return rawHeaders;
  }

  public String[] getHeaders() {
    return headers;
  }
}
