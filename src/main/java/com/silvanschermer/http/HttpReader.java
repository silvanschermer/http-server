package com.silvanschermer.http;

import com.silvanschermer.utils.Logger;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

/** Reads HTTP requests from a socket. */
public class HttpReader {

  /** Instantiates a new HttpReader. */
  public HttpReader() {}

  /**
   * Reads an HTTP request from the given socket and returns it.
   *
   * @param socket the client socket connection
   * @return the parsed {@link HttpRequest}
   * @throws IOException if an I/O error occurs during reading
   */
  public HttpRequest fromSocket(Socket socket) throws IOException {
    BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
    ByteArrayOutputStream headerBytes = readHederBytes(in);
    String[] headers = headerBytes.toString().split("\\r\\n");

    var requestOptions = parseRequestOptions(headers);
    ByteArrayOutputStream contentBytes = readContentBytes(in, requestOptions);

    return new HttpRequest(headerBytes.toString(), headers, contentBytes.toString());
  }

  /**
   * Reads the HTTP headers from the input stream.
   *
   * @param in the input stream to read from
   * @return a byte array containing the header bytes
   * @throws IOException if an I/O error occurs during reading
   */
  public ByteArrayOutputStream readHederBytes(BufferedInputStream in) throws IOException {
    ByteArrayOutputStream headerBytes = new ByteArrayOutputStream();

    for (int prev = -1, curr; (curr = in.read()) != -1; prev = curr) {
      headerBytes.write(curr);
      // verify end of header bytes...
      if (prev == '\r' && curr == '\n') {
        byte[] hb = headerBytes.toByteArray();
        int n = hb.length;
        if (n >= 4
            && hb[n - 4] == '\r'
            && hb[n - 3] == '\n'
            && hb[n - 2] == '\r'
            && hb[n - 1] == '\n') {
          break;
        }
      }
    }

    return headerBytes;
  }

  /**
   * Reads the HTTP content from the input stream based on the request options.
   *
   * @param in the input stream to read from
   * @param options the request options containing content length and chunked transfer encoding
   *     information
   * @return a byte array containing the content bytes
   * @throws IOException if an I/O error occurs during reading
   */
  public ByteArrayOutputStream readContentBytes(BufferedInputStream in, RequestOptions options)
      throws IOException {
    ByteArrayOutputStream contentBytes = new ByteArrayOutputStream();

    byte[] buffer = new byte[8192];
    int n;

    if (!options.chunk() && options.contentLength() > 0) {
      int remaining = options.contentLength();
      while (remaining > 0
          && (n = in.read(buffer, 0, (int) Math.min(buffer.length, remaining))) != -1) {
        contentBytes.write(buffer, 0, n);
        remaining -= n;
      }
    } else if (options.chunk()) {
      Logger.info("'Chunked'");
    }

    return contentBytes;
  }

  /**
   * Parses the request options from the HTTP headers.
   *
   * @param headers the array of HTTP headers
   * @return the parsed {@link RequestOptions}
   */
  public RequestOptions parseRequestOptions(String[] headers) {

    String methodToken = null;
    String identifier = null;
    String protocolVersion = null;
    boolean chunk = false;
    Integer contentLength = null;
    String charset = null;

    for (int i = 0; i < headers.length; i++) {
      String header = headers[i];
      if (i == 0) {
        String[] firstLineSplit = header.split(" ");
        methodToken = firstLineSplit[0];
        identifier = firstLineSplit[1];
        protocolVersion = firstLineSplit[2];
      }

      String headerLower = header.toLowerCase();
      if (headerLower.startsWith("transfer-encoding:") && headerLower.contains("chunked")) {
        chunk = true;
      }

      if (headerLower.startsWith("content-length:")) {
        contentLength = Integer.parseInt(headerLower.split(":", 2)[1].trim());
      }

      if (headerLower.startsWith("charset:")) {
        charset = headerLower.split(":", 2)[1].trim().toLowerCase();
      }
    }

    return new RequestOptions(
        new HttpFirstLine(methodToken, identifier, protocolVersion), chunk, contentLength, charset);
  }
}
