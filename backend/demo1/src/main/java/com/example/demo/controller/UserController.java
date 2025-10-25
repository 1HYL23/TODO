package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 注册
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        User user = userService.register(username, password);
        if (user == null) {
            return "用户名已存在";
        }
        return "注册成功";
    }

    // 登录
    @PostMapping("/login")
    public Object login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user == null) {
            return "用户名或密码错误";
        }
        // 简单返回用户ID（实际生产建议用 token）
        return new LoginResp(user.getId(), user.getUsername());
    }

    // 登录返回对象
    static class LoginResp {
        public Long id;
        public String username;

        public LoginResp(Long id, String username) {
            this.id = id;
            this.username = username;
        }
    }
}
