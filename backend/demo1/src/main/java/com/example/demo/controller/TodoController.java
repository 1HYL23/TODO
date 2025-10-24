package com.example.demo.controller;

import com.example.demo.entity.TodoItem;
import com.example.demo.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

// 必须添加RestController注解让Spring识别为REST API控制器
@RestController
@CrossOrigin(origins = "http://localhost:3000") // 允许前端访问
@RequestMapping("/api/todos") // 配置API的路径前缀
public class TodoController {

    private final TodoRepository repo;

    // 正确的构造方法写法
    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<TodoItem> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public TodoItem create(@RequestBody TodoItem todo) {
        todo.setId(null);
        return repo.save(todo);
    }

    @PatchMapping("/{id}")
    public TodoItem toggle(@PathVariable Long id) {
        Optional<TodoItem> opt = repo.findById(id);
        if (opt.isPresent()) {
            TodoItem item = opt.get();
            item.setCompleted(!item.isCompleted());
            return repo.save(item);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}

