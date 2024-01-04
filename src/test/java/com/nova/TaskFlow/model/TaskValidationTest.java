package com.nova.TaskFlow.model;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

import com.nova.taskflow.model.Task;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
  public void testTitleIsNotBlank() {
    // Arrange
    Task task = createTestTask("", "Sample Description", null, false);
    // Act
    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    // Assert
    assertFalse(violations.isEmpty());
    boolean hasNoBlankViolation =
        violations.stream()
            .anyMatch(
                violation ->
                    violation
                        .getConstraintDescriptor()
                        .getAnnotation()
                        .annotationType()
                        .equals(NotBlank.class));
    assertTrue(hasNoBlankViolation);
  }

  @Test
  public void testTitleIsWithinLengthRange() {
    // Arrange
    Task task = createTestTask("a".repeat(151), "Sample Description", null, false);
    // Act
    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    // Assert
    assertFalse(violations.isEmpty());
    boolean hasSizeViolation =
        violations.stream()
            .anyMatch(
                violation ->
                    violation
                        .getConstraintDescriptor()
                        .getAnnotation()
                        .annotationType()
                        .equals(Size.class));
    assertTrue(hasSizeViolation);
  }

  @Test
  public void testDescriptionCanBeNull() {
    // Arrange
    Task task = createTestTask("Valid Title", null, Instant.now().plusSeconds(86400), false);
    // Act
    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    // Assert
    assertTrue(violations.isEmpty());
  }

  @Test
  public void testDueDateIsWithinAllowedRange() {
    // Arrange
    Task task =
        createTestTask("Valid Title", "Valid Description", Instant.now().minus(1, DAYS), false);
    // Act
    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    // Assert
    assertFalse(violations.isEmpty());
    boolean hasFutureOrPresentViolation =
        violations.stream()
            .anyMatch(
                violation ->
                    violation
                        .getConstraintDescriptor()
                        .getAnnotation()
                        .annotationType()
                        .equals(FutureOrPresent.class));
    assertTrue(hasFutureOrPresentViolation);
  }

  @Test
  public void testNoViolationsWhenValidData() {
    // Arrange
    Task task =
        createTestTask("Valid Title", "Valid Description", Instant.now().plusSeconds(86400), false);
    // Act
    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    // Assert
    assertTrue(violations.isEmpty());
  }

  private Task createTestTask(
      String title, String description, Instant dueDate, boolean completed) {
    Task task = new Task();
    task.setTitle(title);
    task.setDescription(description);
    task.setDueDate(dueDate);
    task.setCompleted(completed);
    return task;
  }
}
