package com.example.planboardbackend.service.impl;

import com.example.planboardbackend.dto.ResponseUserDto;
import com.example.planboardbackend.mapper.UserMapper;
import com.example.planboardbackend.model.User;
import com.example.planboardbackend.repository.UserRepository;
import com.example.planboardbackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public boolean emailIsExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public ResponseUserDto getUser(User user) {
        return userMapper
                .toResponseUserDto(findByEmail(user.getEmail()));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }
}
