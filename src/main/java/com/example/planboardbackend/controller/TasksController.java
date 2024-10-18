package com.example.planboardbackend.controller;

import com.example.planboardbackend.api.TasksApi;
import com.example.planboardbackend.model.dto.RequestCreateTaskDto;
import com.example.planboardbackend.model.dto.ResponseTaskDto;
import com.example.planboardbackend.model.entity.User;
import com.example.planboardbackend.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TasksController implements TasksApi {
    TaskService taskService;


    @PostMapping
    public ResponseEntity<ResponseTaskDto> createTask(@AuthenticationPrincipal User user,
                                                      @Validated @RequestBody RequestCreateTaskDto requestCreateTaskDto) {

        ResponseTaskDto savedTask = taskService.save(user, requestCreateTaskDto);
        return ResponseEntity.ok(savedTask);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTaskDto>> getAllTasks(@AuthenticationPrincipal User user) {
        List<ResponseTaskDto> tasks = taskService.getAllTasks(user);
        return ResponseEntity.ok(tasks);
    }
}