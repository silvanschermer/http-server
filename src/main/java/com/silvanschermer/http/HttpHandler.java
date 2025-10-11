package com.silvanschermer.http;

import com.silvanschermer.utils.Logger;

import java.net.Socket;

public class HttpHandler implements Runnable {

    private Socket socket;

    public HttpHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Logger.info("Running HTTP Handler");
    }
}
