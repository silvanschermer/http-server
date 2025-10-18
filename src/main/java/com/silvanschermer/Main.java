package com.silvanschermer;

import com.silvanschermer.http.HttpServer;

/** The main application entry point. */
public class Main {

  /**
   * Starts the HTTP server on a specified port.
   *
   * @param args Command-line arguments, which may include a custom port using --port=<port_number>.
   */
  static void main(String[] args) {
    int port = 8080;
    for (String arg : args) {
      Integer parsedPort = parsePort(arg);
      port = parsedPort == null ? port : parsedPort;
    }

    new HttpServer(port).start();
  }

  /**
   * Parses a command-line argument to extract the port number.
   *
   * @param arg The command-line argument, expected to start with --port= followed by the port
   *     number.
   * @return The parsed port number if the argument is valid; otherwise, null.
   */
  private static Integer parsePort(String arg) {
    return arg.startsWith("--port=") ? Integer.parseInt(arg.substring("--port=".length())) : null;
  }
}
