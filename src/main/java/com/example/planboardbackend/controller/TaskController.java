package com.example.planboardbackend.controller;

import com.example.planboardbackend.api.TaskApi;
import com.example.planboardbackend.dto.RequestUpdateTaskDto;
import com.example.planboardbackend.dto.ResponseTaskDto;
import com.example.planboardbackend.model.User;
import com.example.planboardbackend.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskController implements TaskApi {
    TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTaskDto> getTask(@AuthenticationPrincipal User user,
                                                   @PathVariable Long id) {
        ResponseTaskDto task = taskService.getTask(id, user);
        return ResponseEntity.ok(task);
    }
    @PatchMapping("{id}/update")
    public ResponseEntity<ResponseTaskDto> updateTask(@AuthenticationPrincipal User user,
                                                      @PathVariable Long id,
                                                      @Validated @RequestBody RequestUpdateTaskDto requestUpdateTaskDto){
        ResponseTaskDto task = taskService.updateTask(user, id, requestUpdateTaskDto);
        return ResponseEntity.ok(task);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteTask(@AuthenticationPrincipal User user,
                                        @PathVariable Long id) {
        taskService.deleteTask(user, id);
        return ResponseEntity.ok().build();
    }
}
