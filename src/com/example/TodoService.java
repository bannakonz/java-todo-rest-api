package com.example;

import java.util.ArrayList;
import java.util.List;

public class TodoService {
    private static List<Todo> todos = new ArrayList<>();
    private static int currentId = 1;

    static {
        todos.add(new Todo(currentId++, "Learn Java", false));
        todos.add(new Todo(currentId++, "Create a REST API", true));
    }

    public List<Todo> getAllTodos() {
        return todos;
    }

    public Todo getTodoById(int id) {
        return todos.stream().filter(todo -> todo.getId() == id).findFirst().orElse(null);
    }

    public void addTodo(Todo todo) {
        todo.setId(currentId++);
        todos.add(todo);
    }

    public void deleteTodo(int id) {
        todos.removeIf(todo -> todo.getId() == id);
    }
}
