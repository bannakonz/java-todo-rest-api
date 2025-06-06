package com.example.utils;

import com.example.TodoController;

import java.io.*;
import java.net.*;

public class HttpServer {
    private static final int PORT = 8080;

    public static void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Hello Todo API");
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

            // Log the incoming request
            System.out.println("Incoming request: " + requestLine);

            // Consume the remaining headers (if any)
            String line;
            while (!(line = reader.readLine()).isEmpty()) {
                // You could log headers if needed
                System.out.println("Header: " + line);
            }

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);

            if (requestLine != null && requestLine.startsWith("GET")) {
                // Route the request based on the URL
                TodoController.handleRequest(requestLine, writer);
            } else {
                writer.println("HTTP/1.1 400 Bad Request");
                writer.println("Content-Type: text/plain");
                writer.println();
                writer.println("400 Bad Request");
            }

            writer.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
