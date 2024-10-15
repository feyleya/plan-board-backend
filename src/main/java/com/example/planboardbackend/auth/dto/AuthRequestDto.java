package com.example.planboardbackend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "Authentication request for user login")
public class AuthRequestDto {
    @Schema(description = "User's email address", example = "example@mail.com")
    @NotBlank(message = "Email must be provided")
    String email;

    @Schema(description = "User's password", example = "password123")
    @NotNull(message = "Password must be provided")
    @Size(min = 3, max = 20, message = "The password size must be between 3 and 20 characters")
    String password;
}