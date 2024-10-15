package com.example.planboardbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Schema(description = "Response DTO representing a user")
@Value
public class ResponseUserDto {
    @Schema(description = "Unique identifier of the user", example = "42")
    Long id;

    @Schema(description = "Username of the user", example = "Ivan")
    String username;

    @Schema(description = "Email address of the user", example = "mail@example.com")
    String email;
}
