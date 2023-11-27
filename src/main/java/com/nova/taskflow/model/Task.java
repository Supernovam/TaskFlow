package com.nova.taskflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import java.time.Instant;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  @NotBlank(message = "Title can not be blank.")
  @Size(max = 150, message = "Title must be 150 characters or less.")
  private String title;

    private String description;

  @FutureOrPresent(message = "Due date must be in the present or future.")
  private Instant dueDate;

    private boolean completed;
}
