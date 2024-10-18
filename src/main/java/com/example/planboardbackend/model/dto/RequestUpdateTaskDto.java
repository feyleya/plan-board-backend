package com.example.planboardbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for updating a task")
public class RequestUpdateTaskDto {
    @NotNull(message = "The title must not be empty")
    @Size(min = 3, max = 50, message = "The title size must be from 3 to 50 characters")
    @Schema(description = "Title of the task", example = "Updated Task Title")
    String title;

    @Size(max = 1000, message = "Description size must be up to 1000 characters")
    @Schema(description = "Description of the task", example = "This is an updated description of the task.")
    String description;

    @Schema(description = "Completion status of the task", example = "false")
    Boolean iscompleted;
}
