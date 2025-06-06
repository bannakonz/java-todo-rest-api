package com.example;

import java.io.*;
import java.util.List;

public class TodoController {

    private static TodoService todoService = new TodoService();

    public static void handleRequest(String requestLine, PrintWriter writer) {
        if (requestLine.startsWith("GET /todos")) {
            // Check if there's an 'id' query parameter
            String[] parts = requestLine.split(" ");
            if (parts.length > 1 && parts[1].contains("?id=")) {
                // Extract the id parameter from the URL query
                String[] query = parts[1].split("\\?id=");
                if (query.length > 1) {
                    int id = Integer.parseInt(query[1]);
                    // Fetch the specific Todo by id
                    Todo todo = todoService.getTodoById(id);
                    if (todo != null) {
                        writer.println("HTTP/1.1 200 OK");
                        writer.println("Content-Type: text/plain");
                        writer.println();
                        writer.println("Todo ID: " + todo.getId() + ", Title: " + todo.getTitle() + ", Completed: " + todo.isCompleted());
                        return; // Return early after finding the Todo
                    } else {
                        writer.println("HTTP/1.1 404 Not Found");
                        writer.println("Content-Type: text/plain");
                        writer.println();
                        writer.println("Todo not found");
                        return; // Return early if the Todo isn't found
                    }
                }
            }

            // If no 'id' query parameter, list all todos
            List<Todo> todos = todoService.getAllTodos();
            StringBuilder response = new StringBuilder();
            for (Todo todo : todos) {
                response.append("ID: ").append(todo.getId())
                        .append(" Title: ").append(todo.getTitle())
                        .append(" Completed: ").append(todo.isCompleted()).append("\n");
            }

            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/plain");
            writer.println();
            writer.println(response);
        } else if (requestLine.startsWith("GET /todo")) {
            // Handle request for individual todo by id (e.g., /todo?id=1)
            String[] parts = requestLine.split(" ");
            String[] query = parts[1].split("=");
            if (query.length > 1) {
                int id = Integer.parseInt(query[1]);
                Todo todo = todoService.getTodoById(id);
                if (todo != null) {
                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type: text/plain");
                    writer.println();
                    writer.println("Todo ID: " + todo.getId() + ", Title: " + todo.getTitle() + ", Completed: " + todo.isCompleted());
                } else {
                    writer.println("HTTP/1.1 404 Not Found");
                    writer.println("Content-Type: text/plain");
                    writer.println();
                    writer.println("Todo not found");
                }
            }
        } else {
            writer.println("HTTP/1.1 404 Not Found");
            writer.println("Content-Type: text/plain");
            writer.println();
            writer.println("404 Not Found");
        }
    }
}
