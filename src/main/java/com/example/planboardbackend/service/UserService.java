package com.example.planboardbackend.service;

import com.example.planboardbackend.dto.ResponseUserDto;
import com.example.planboardbackend.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(User user);
    User findByEmail(String email);

    boolean emailIsExists(String email);
    ResponseUserDto getUser(User user);
}