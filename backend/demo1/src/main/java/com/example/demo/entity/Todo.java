package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Boolean completed = false;

    // 多对一关系，每个任务属于一个用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Todo() {}
    public Todo(String content, User user) {
        this.content = content;
        this.user = user;
        this.completed = false;
    }

    // getter和setter方法...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

