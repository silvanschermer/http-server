package com.silvanschermer.http;

/** Represents an HTTP request. */
public class HttpRequest {
  String rawHeaders;
  String[] headers;

  String rawContent;

  /**
   * Constructs an instance of {@link HttpRequest}.
   *
   * @param rawHeaders the raw HTTP headers
   * @param headers the parsed HTTP headers
   * @param rawContent the raw content of the request
   */
  public HttpRequest(String rawHeaders, String[] headers, String rawContent) {
    this.rawHeaders = rawHeaders;
    this.headers = headers;
    this.rawContent = rawContent;
  }

  /**
   * Gets the raw HTTP headers.
   *
   * @return the raw headers as a string
   */
  public String getRawHeaders() {
    return rawHeaders;
  }

  /**
   * Gets the parsed HTTP headers.
   *
   * @return the headers as an array of strings
   */
  public String[] getHeaders() {
    return headers;
  }

  /**
   * Gets the raw content of the HTTP request.
   *
   * @return the raw content as a string
   */
  public String getRawContent() {
    return rawContent;
  }
}
