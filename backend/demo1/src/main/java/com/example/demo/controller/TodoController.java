package com.example.demo.controller;

import com.example.demo.entity.Todo;
import com.example.demo.entity.User;
import com.example.demo.service.TodoService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService userService;

    // 获取当前用户的所有TODO
    @GetMapping("/list")
    public List<Todo> list(@RequestParam Long userId) {
        User user = userService.findById(userId);
        return todoService.getTodosByUser(user);
    }

    // 新增
    @PostMapping("/add")
    public Object add(@RequestParam Long userId, @RequestParam String content) {
        User user = userService.findById(userId);
        return todoService.addTodo(user, content);
    }

    // 修改
    @PostMapping("/update")
    public Object update(@RequestParam Long id, @RequestParam(required = false) String content, @RequestParam(required = false) Boolean completed) {
        Todo todo = todoService.updateTodo(id, content, completed);
        if (todo != null) return todo;
        return "未找到该任务";
    }

    // 删除
    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        if (todoService.deleteTodo(id)) {
            return "删除成功";
        } else {
            return "未找到该任务";
        }
    }
}

