package com.silvanschermer.http;

import com.silvanschermer.utils.Logger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents an HTTP server that listens for incoming requests and handles them using {@link
 * HttpHandler}.
 */
public class HttpServer {

  private final int port;
  private final ExecutorService executor;

  /**
   * Constructs an instance of {@link HttpServer} with the specified port.
   *
   * @param port the port number to listen on
   */
  public HttpServer(int port) {
    this.port = port;
    this.executor = Executors.newVirtualThreadPerTaskExecutor();
  }

  /** Starts the HTTP server and begins listening for incoming client connections. */
  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      Logger.info("HTTP Server started on port " + port);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected");
        executor.submit(new HttpHandler(clientSocket));
      }
    } catch (IOException e) {
      Logger.error("Error: " + e.getMessage());
    }
  }
}
