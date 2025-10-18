package com.silvanschermer.utils;

/** A simple logger. */
public class Logger {

  /**
   * Logs a message to stdout.
   *
   * @param message The message to be logged.
   */
  public static void info(String message) {
    System.out.println(message);
  }

  /**
   * Logs a message to stdout.
   *
   * @param message The message to be logged.
   */
  public static void error(String message) {
    System.err.println(message);
  }
}
