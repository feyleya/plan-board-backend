package com.example.planboardbackend.controller;

import com.example.planboardbackend.api.UserApi;
import com.example.planboardbackend.model.dto.ResponseUserDto;
import com.example.planboardbackend.model.entity.User;
import com.example.planboardbackend.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/user")
public class UserController implements UserApi {
    UserService userService;

    @GetMapping
    public ResponseEntity<ResponseUserDto> getUser(@AuthenticationPrincipal User user) {
        ResponseUserDto responseUser = userService.getUser(user);
        log.info("User with username - {} and email {}",
                responseUser.getUsername(),
                responseUser.getEmail());
        return ResponseEntity.ok(responseUser);
    }
}
