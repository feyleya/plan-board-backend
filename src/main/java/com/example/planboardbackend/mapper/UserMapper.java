package com.example.planboardbackend.mapper;

import com.example.planboardbackend.dto.ResponseUserDto;
import com.example.planboardbackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(RegistrationUserDto registrationUserDto);

    @Mapping(source = "name", target = "username")
    ResponseUserDto toResponseUserDto(User user);
}
