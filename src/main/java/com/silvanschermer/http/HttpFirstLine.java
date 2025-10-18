package com.silvanschermer.http;

/**
 * Represents the first line of an HTTP request or response.
 *
 * @param methodToken the HTTP method token (e.g., GET, POST)
 * @param identifier the resource identifier (path and query string)
 * @param protocolVersion the HTTP protocol version (e.g., HTTP/1.1)
 */
public record HttpFirstLine(String methodToken, String identifier, String protocolVersion) {}
