package com.example.utils;

import java.io.*;
import java.net.*;

public class HttpServer {
    private static final int PORT = 8080;

    public static void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is running on port " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            handleRequest(socket);
        }
    }

    private static void handleRequest(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String requestLine = reader.readLine();

            System.out.println("Request: " + requestLine);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);

            if (requestLine != null && requestLine.startsWith("GET")) {
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Type: text/plain");
                writer.println();
                writer.println("Hello, World!");
            } else {
                writer.println("HTTP/1.1 404 Not Found");
                writer.println("Content-Type: text/plain");
                writer.println();
                writer.println("404 Not Found");
            }

            writer.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
