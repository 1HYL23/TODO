import React, { useState, useEffect } from "react";

const API_URL = "http://localhost:8080/api/todos";

function App() {
  const [todos, setTodos] = useState([]);           // 当前的待办事项列表
  const [input, setInput] = useState("");           // 新增事项内容
  const [loading, setLoading] = useState(false);    // 网络加载标记

  // 获取所有事项
  const fetchTodos = async () => {
    setLoading(true);
    const res = await fetch(API_URL);
    const data = await res.json();
    setTodos(data);
    setLoading(false);
  };

  // 初次加载时请求事项列表
  useEffect(() => {
    fetchTodos();
  }, []);

  // 新增事项
  const addTodo = async (e) => {
    e.preventDefault();
    if (!input.trim()) return;
    const res = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ text: input, completed: false }),
    });
    if (res.ok) {
      setInput("");
      fetchTodos();
    }
  };

  // 切换完成状态
  const toggleTodo = async (id) => {
    const res = await fetch(`${API_URL}/${id}`, { method: "PATCH" });
    if (res.ok) fetchTodos();
  };

  // 删除事项
  const deleteTodo = async (id) => {
    const res = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    if (res.ok) fetchTodos();
  };

  return (
    <div style={{ width: 400, margin: "40px auto", fontFamily: "sans-serif" }}>
      <h2>TODO 清单</h2>
      <form onSubmit={addTodo} style={{ display: "flex", marginBottom: 20 }}>
        <input
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="要做什么？"
          style={{ flex: 1, padding: 8 }}
        />
        <button type="submit" style={{ marginLeft: 8 }}>添加</button>
      </form>
      {loading ? <p>加载中...</p> : (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {todos.map((todo) => (
            <li key={todo.id} style={{ display: "flex", alignItems: "center", marginBottom: 8}}>
              <span
                onClick={() => toggleTodo(todo.id)}
                style={{
                  textDecoration: todo.completed ? "line-through" : "none",
                  flex: 1,
                  cursor: "pointer"
                }}
              >
                {todo.text}
              </span>
              <button onClick={() => deleteTodo(todo.id)} style={{ marginLeft: 8 }}>删除</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default App;
