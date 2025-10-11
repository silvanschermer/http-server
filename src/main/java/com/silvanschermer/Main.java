package com.silvanschermer;

import com.silvanschermer.utils.Logger;

public class Main {
    public static void main(String[] args) {
        int port = 8080;
        for (String arg : args) {
            Integer parsedPort = parsePort(arg);
            port = parsedPort == null ? port : parsedPort;
        }

        new HttpServer(port).start();
    }


    private static Integer parsePort(String arg) {
        return arg.startsWith("--port=") ? Integer.parseInt(arg.substring("--port=".length())) : null;
    }
}
