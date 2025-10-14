package com.silvanschermer.http;

public class HttpRequest {
  String rawHeaders;
  String[] headers;

  String rawContent;

  public HttpRequest(String rawHeaders, String[] headers, String rawContent) {
    this.rawHeaders = rawHeaders;
    this.headers = headers;
    this.rawContent = rawContent;
  }

  public String getRawHeaders() {
    return rawHeaders;
  }

  public String[] getHeaders() {
    return headers;
  }

  public String getRawContent() {
    return rawContent;
  }
}
