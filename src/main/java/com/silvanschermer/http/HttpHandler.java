package com.silvanschermer.http;

import java.io.IOException;
import java.net.Socket;

/** Handles HTTP requests and processes them. */
public class HttpHandler implements Runnable {

  private final Socket socket;

  /**
   * Constructs an instance of {@link HttpHandler}.
   *
   * @param socket the client socket connection
   */
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
