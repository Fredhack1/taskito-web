package com.taskito_web.backend.service;

import com.taskito_web.backend.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task createTask(Task task);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);
}
