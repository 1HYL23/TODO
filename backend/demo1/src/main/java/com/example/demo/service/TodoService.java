package com.example.demo.service;

import com.example.demo.entity.Todo;
import com.example.demo.entity.User;
import com.example.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getTodosByUser(User user) {
        return todoRepository.findByUser(user);
    }

    public Todo addTodo(User user, String content) {
        Todo todo = new Todo(content, user);
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, String content, Boolean completed) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            if (content != null) todo.setContent(content);
            if (completed != null) todo.setCompleted(completed);
            return todoRepository.save(todo);
        }
        return null;
    }

    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

