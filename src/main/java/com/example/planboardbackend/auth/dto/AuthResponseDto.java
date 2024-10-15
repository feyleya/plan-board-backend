package com.example.planboardbackend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(description = "Access token response")
public class AuthResponseDto {
    @Schema(description = "accessToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0I...")
    String accessToken;
    @Schema(description = "refreshToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInJvbGUiOiJVU0VSIiwiaWF0...")
    String refreshToken;
}
