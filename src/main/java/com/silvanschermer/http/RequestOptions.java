package com.silvanschermer.http;

public record RequestOptions(boolean chunk, int contentLength, String charset) {}
