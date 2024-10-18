package com.example.planboardbackend.service.impl;

import com.example.planboardbackend.model.dto.RequestCreateTaskDto;
import com.example.planboardbackend.model.dto.RequestUpdateTaskDto;
import com.example.planboardbackend.model.dto.ResponseTaskDto;
import com.example.planboardbackend.exception.custom.TaskNotFoundException;
import com.example.planboardbackend.model.mapper.TaskMapper;
import com.example.planboardbackend.model.entity.Task;
import com.example.planboardbackend.model.entity.User;
import com.example.planboardbackend.model.repository.TaskRepository;
import com.example.planboardbackend.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;
    TaskMapper taskMapper;


    @Transactional
    @Override
    public ResponseTaskDto save(User user, RequestCreateTaskDto requestCreateTaskDto) {
        Task task = taskMapper.requestTaskCreateDtoToTask(requestCreateTaskDto);
        task.setUser(user);
        return taskMapper.taskToResponseTaskCreateDto(taskRepository.save(task));
    }

    @Override
    public List<ResponseTaskDto> getAllTasks(User user) {
        List<ResponseTaskDto> tasks = taskRepository
                .findByUser(user)
                .stream()
                .map(taskMapper::taskToResponseTaskCreateDto)
                .toList();
        return tasks;
    }

    @Override
    public ResponseTaskDto getTask(Long id, User user) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return taskMapper.taskToResponseTaskCreateDto(task);
    }

    @Transactional
    @Override
    public ResponseTaskDto updateTask(User user, Long id, RequestUpdateTaskDto requestUpdateTaskDto) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        updateTaskFields(task, requestUpdateTaskDto);

        taskRepository.save(task);

        return taskMapper.taskToResponseTaskCreateDto(task);
    }

    @Transactional
    @Override
    public void deleteTask(User user, Long id) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    private void updateTaskFields(Task task, RequestUpdateTaskDto dto) {
        if (!Objects.equals(task.getTitle(), dto.getTitle())) {
            task.setTitle(dto.getTitle());
        }
        if (!Objects.equals(task.getDescription(), dto.getDescription())) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getIscompleted() != null) {
            task.setIscompleted(dto.getIscompleted());
            task.setCompletedAt(dto.getIscompleted() ? LocalDateTime.now() : null);
        }
    }
}
