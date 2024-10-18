package com.example.planboardbackend.service;

import com.example.planboardbackend.model.dto.RequestCreateTaskDto;
import com.example.planboardbackend.model.dto.RequestUpdateTaskDto;
import com.example.planboardbackend.model.dto.ResponseTaskDto;
import com.example.planboardbackend.model.entity.User;

import java.util.List;

public interface TaskService {
    ResponseTaskDto save(User user, RequestCreateTaskDto requestCreateTaskDto);

    List<ResponseTaskDto> getAllTasks(User user);

    ResponseTaskDto getTask(Long id, User user);

    ResponseTaskDto updateTask(User user, Long id, RequestUpdateTaskDto requestUpdateTaskDto);

    void deleteTask(User user, Long id);

}
