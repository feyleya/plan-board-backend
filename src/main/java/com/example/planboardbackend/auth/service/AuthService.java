package com.example.planboardbackend.auth.service;

import com.example.planboardbackend.auth.dto.AuthRequestDto;
import com.example.planboardbackend.auth.dto.AuthResponseDto;
import com.example.planboardbackend.auth.dto.RefreshRequestDto;
import com.example.planboardbackend.auth.dto.RegistrationUserDto;
import com.example.planboardbackend.exception.custom.AuthenticationFailedException;
import com.example.planboardbackend.exception.custom.EmailAlreadyExistsException;
import com.example.planboardbackend.exception.custom.InvalidTokenException;
import com.example.planboardbackend.exception.custom.PasswordMismatchException;
import com.example.planboardbackend.kafka.dto.EmailSendingDto;
import com.example.planboardbackend.kafka.service.KafkaService;
import com.example.planboardbackend.model.mapper.UserMapper;
import com.example.planboardbackend.model.entity.Role;
import com.example.planboardbackend.model.entity.User;
import com.example.planboardbackend.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthService {
    UserService userService;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    JwtService jwtService;
    AuthenticationManager authenticationManager;
    KafkaService kafkaService;
    RedisTemplate<String, String> redisTemplate;

    public AuthResponseDto signup(RegistrationUserDto registrationUserDto) {
        if (!(registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword()))) {
            throw new PasswordMismatchException();
        }
        String email = registrationUserDto.getEmail();
        if (userService.emailIsExists(email)) {
            throw new EmailAlreadyExistsException(email);
        }
        User user = userMapper.toUser(registrationUserDto);
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRole(Role.USER);

        userService.createUser(user);
        sendKafkaMessage(user);
        log.info("Create user - {}, email - {}, password - {}", user.getName(), user.getEmail(), user.getPassword());

        String refreshToken = jwtService.generateToken(user, false);
        saveRefreshToken(user.getId(), refreshToken);

        return new AuthResponseDto(
                jwtService.generateToken(user, true),
                refreshToken
        );
    }

    public AuthResponseDto signin(AuthRequestDto requestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Incorrect email or password");
        }
        User user = Optional.ofNullable(userService.findByEmail(requestDto.getEmail()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String refreshToken = jwtService.generateToken(user, false);
        saveRefreshToken(user.getId(), refreshToken);

        return new AuthResponseDto(
                jwtService.generateToken(user, true),
                refreshToken
        );
    }

    public AuthResponseDto refreshToken(RefreshRequestDto requestDto) {
        try {
            String refreshToken = requestDto.getRefreshToken();
            String email = jwtService.extractUsername(refreshToken, false);
            User user = userService.findByEmail(email);

            String storedRefreshToken = redisTemplate.opsForValue().get(user.getId().toString());
            if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
                throw new InvalidTokenException("Refresh token expired or invalid. Please re-authenticate.");
            }

            if (!jwtService.isValid(refreshToken, user, false)) {
                throw new InvalidTokenException("Invalid refresh token");
            }

            String accessToken = jwtService.generateToken(user, true);
            return new AuthResponseDto(accessToken, refreshToken);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    public void logout(String refreshToken) {
        String email = jwtService.extractUsername(refreshToken, false);
        User user = userService.findByEmail(email);
        deleteRefreshToken(user.getId());
        log.info("User {} has successfully logged out", user.getEmail());
    }

    private void sendKafkaMessage(User user) {
        EmailSendingDto emailSendingDto = new EmailSendingDto(
                user.getEmail(),
                "Thank you for registration!",
                "Welcome " + user.getName() + " " + "!"
        );
        kafkaService.sendRegisterEmailMessage(emailSendingDto);
        log.info("EmailSendingDto: {}", emailSendingDto);
    }

    private void saveRefreshToken(Long id, String refreshToken) {
        redisTemplate.opsForValue().set(id.toString(), refreshToken, 604800000, TimeUnit.MILLISECONDS);
    }

    private void deleteRefreshToken(Long id) {
        redisTemplate.delete(id.toString());
    }
}