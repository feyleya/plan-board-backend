package com.example.planboardbackend.controller;

import com.example.planboardbackend.api.AuthApi;
import com.example.planboardbackend.auth.dto.AuthRequestDto;
import com.example.planboardbackend.auth.dto.AuthResponseDto;
import com.example.planboardbackend.auth.dto.RefreshRequestDto;
import com.example.planboardbackend.auth.dto.RegistrationUserDto;
import com.example.planboardbackend.auth.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController implements AuthApi {
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody @Validated RegistrationUserDto registrationUserDto) {
        return ResponseEntity.ok(authService.signup(registrationUserDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody @Validated AuthRequestDto requestDto) {
        return ResponseEntity.ok(authService.signin(requestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody @Validated RefreshRequestDto refreshDto) {
        authService.logout(refreshDto.getRefreshToken());
        return ResponseEntity.ok("Successfully logged out");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshAccessToken(@RequestBody @Validated RefreshRequestDto requestDto) {
        return ResponseEntity.ok(authService.refreshToken(requestDto));
    }
}
