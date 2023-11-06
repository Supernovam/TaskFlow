package com.nova.taskflow.service;

import com.nova.taskflow.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    Task getTaskById(Long id);

    List<Task> getAllTasks();

    Task updateTask(Long id, Task taskDetails);

    void deleteTask(Long id);
}