package com.example.planboardbackend.model.mapper;

import com.example.planboardbackend.model.dto.RequestCreateTaskDto;
import com.example.planboardbackend.model.dto.ResponseTaskDto;
import com.example.planboardbackend.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "iscompleted", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Task requestTaskCreateDtoToTask(RequestCreateTaskDto requestCreateTaskDto);

    @Mapping(source = "iscompleted", target = "iscompleted")
    ResponseTaskDto taskToResponseTaskCreateDto(Task task);
}