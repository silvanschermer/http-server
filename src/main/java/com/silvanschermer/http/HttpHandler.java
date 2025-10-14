package com.silvanschermer.http;

import java.io.IOException;
import java.net.Socket;

public class HttpHandler implements Runnable {

  private final Socket socket;

  public HttpHandler(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      HttpRequest request = new HttpReader().fromSocket(socket);

      socket.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
