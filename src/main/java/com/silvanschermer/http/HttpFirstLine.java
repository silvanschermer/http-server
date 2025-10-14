package com.silvanschermer.http;

public record HttpFirstLine(String methodToken, String identifier, String protocolVersion) {}
