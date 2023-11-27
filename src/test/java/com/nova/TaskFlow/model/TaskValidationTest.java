package com.nova.TaskFlow.model;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

import com.nova.taskflow.model.Task;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TaskValidationTest {

  private static ValidatorFactory validatorFactory;
  private static Validator validator;

  @BeforeAll
  public static void createValidator() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @AfterAll
  public static void close() {
    validatorFactory.close();
  }

  @Test
  public void testTitleNotBlank() {
    Task task = new Task();
    task.setTitle(""); // blank title
    task.setDescription("Sample Description");
    task.setDueDate(null);
    task.setCompleted(false);

    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Title can not be blank.")));
  }

  @Test
  public void testTitleLength() {
    Task task = new Task();
    task.setTitle("a".repeat(151)); // title longer than 150 characters
    task.setDescription("Sample Description");
    task.setDueDate(null);
    task.setCompleted(false);

    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    assertFalse(violations.isEmpty());
    assertTrue(
        violations.stream()
            .anyMatch(v -> v.getMessage().equals("Title must be 150 characters or less.")));
  }

  @Test
  public void testDescriptionNotNull() {
    Task task = new Task();
    task.setTitle("Valid Title");
    task.setDescription(null); // null description
    task.setDueDate(Instant.now());
    task.setCompleted(false);

    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    assertFalse(violations.isEmpty());
    // TODO: specific validation message
  }

  @Test
  public void testDueDateInFuture() {
    Task task = new Task();
    task.setTitle("Valid Title");
    task.setDescription("Valid Description");
    task.setDueDate(Instant.now().minus(1, DAYS)); // Due date in the past
    task.setCompleted(false);

    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    assertFalse(violations.isEmpty());
    // TODO: specific validation message
  }

  @Test
  public void testNoViolationsWithValidData() {
    Task task = new Task();
    task.setTitle("Valid Title");
    task.setDescription("Valid Description");
    task.setDueDate(Instant.now().plusSeconds(86400)); // Valid due date in the future
    task.setCompleted(false);

    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    assertTrue(violations.isEmpty());
  }
}
