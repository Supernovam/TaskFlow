package com.nova.TaskFlow.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nova.taskflow.model.Task;
import com.nova.taskflow.repository.TaskRepository;
import com.nova.taskflow.service.TaskServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

  @Mock private TaskRepository taskRepository;

  @InjectMocks private TaskServiceImpl taskService;

  private Task sampleTask;

  @BeforeEach
  public void setUp() {
    sampleTask = new Task();
    sampleTask.setId(1L);
  }

  @Test
  public void testCreateTask() {
    when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

    Task result = taskService.createTask(sampleTask);

    assertNotNull(result);
    assertEquals(sampleTask, result);
  }

  @Test
  public void testGetTaskById() {
    when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

    Task result = taskService.getTaskById(1L);

    assertNotNull(result);
    assertEquals(sampleTask, result);
  }

  @Test
  public void testGetAllTasks() {
    when(taskRepository.findAll()).thenReturn(Collections.singletonList(sampleTask));

    List<Task> result = taskService.getAllTasks();

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals(sampleTask, result.get(0));
  }

  @Test
  public void testUpdateTask() {
    Task updatedTaskDetails = new Task(); // Initialize with updated data
    when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
    when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

    Task result = taskService.updateTask(1L, updatedTaskDetails);

    assertNotNull(result);
    assertEquals(sampleTask, result); // Verify the updated task is returned
  }

  @Test
  public void testDeleteTask() {
    when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
    doNothing().when(taskRepository).delete(any(Task.class));

    taskService.deleteTask(1L);

    verify(taskRepository, times(1))
        .delete(sampleTask); // Verify that delete method was called on repository
  }

  @Test
  public void testGetTaskById_NotFound() {
    when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> taskService.getTaskById(1L));

    String expectedMessage = "Task with id 1 not found";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
}
