package com.example;

import com.example.utils.HttpServer;

public class App {
    public static void main(String[] args) throws Exception {
        // Start the HTTP server
        System.out.println("Hello Todo API");
        HttpServer.startServer();
    }
}
