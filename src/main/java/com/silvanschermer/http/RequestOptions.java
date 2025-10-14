package com.silvanschermer.http;

public record RequestOptions(
    HttpFirstLine firstLine, boolean chunk, Integer contentLength, String charset) {}
