package com.silvanschermer.http;

public record RequestOptions(boolean chunk, Integer contentLength, String charset) {}
