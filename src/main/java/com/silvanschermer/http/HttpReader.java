package com.silvanschermer.http;

import com.silvanschermer.utils.Logger;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HttpReader {
  public HttpReader() {}

  public HttpRequest fromSocket(Socket socket) throws IOException {
    BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
    ByteArrayOutputStream headerBytes = readHederBytes(in);
    String[] headers = headerBytes.toString().split("\\r\\n");

    var RequestOptions = parseRequestOptions(headers);
    ByteArrayOutputStream contentBytes = readContentBytes(in, RequestOptions);

    return new HttpRequest(headerBytes.toString(), headers, contentBytes.toString());
  }

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
