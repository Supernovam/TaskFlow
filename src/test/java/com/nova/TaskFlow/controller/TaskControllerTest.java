package com.nova.TaskFlow.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import com.nova.taskflow.controller.TaskController;
import com.nova.taskflow.model.Task;
import com.nova.taskflow.service.TaskService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TaskControllerTest {

  @Mock private TaskService taskService;

  @InjectMocks private TaskController taskController;

  private Task sampleTask;

  @BeforeEach
  public void setUp() {
    sampleTask = new Task();
    sampleTask.setId(1L);
  }

  @Test
  public void testCreateTask() {
    when(taskService.createTask(any(Task.class))).thenReturn(sampleTask);

    ResponseEntity<Task> response = taskController.createTask(new Task());

    assertEquals(OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(sampleTask, response.getBody());
  }

  @Test
  public void testGetTaskById() {
    when(taskService.getTaskById(1L)).thenReturn(sampleTask);

    ResponseEntity<Task> response = taskController.getTaskById(1L);

    assertEquals(OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(sampleTask, response.getBody());
  }

  @Test
  public void testGetAllTasks() {
    when(taskService.getAllTasks()).thenReturn(Collections.singletonList(sampleTask));

    ResponseEntity<List<Task>> response = taskController.getAllTasks();

    assertEquals(OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isEmpty());
  }

  @Test
  public void testUpdateTask() {
    Task updatedTask = new Task();
    updatedTask.setId(1L);
    when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

    ResponseEntity<Task> response = taskController.updateTask(1L, new Task());

    assertEquals(OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(updatedTask, response.getBody());
  }

  @Test
  public void testDeleteTask() {
    doNothing().when(taskService).deleteTask(1L);

    ResponseEntity<Void> response = taskController.deleteTask(1L);

    assertEquals(OK, response.getStatusCode());
  }

}
