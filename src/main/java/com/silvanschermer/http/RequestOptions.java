package com.silvanschermer.http;

/** Represents the options for an HTTP request. */
public record RequestOptions(
    HttpFirstLine firstLine, boolean chunk, Integer contentLength, String charset) {}
