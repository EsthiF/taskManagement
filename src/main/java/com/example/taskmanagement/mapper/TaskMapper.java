package com.example.taskmanagement.mapper;

import com.example.taskmanagement.dto.TaskDto;
import com.example.taskmanagement.model.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "assignedManagerFirstName", source = "manager.firstName")
    @Mapping(target = "assignedManagerLastName", source = "manager.lastName")
    @Mapping(target = "assignedManagerId", source = "manager.id")
    @Mapping(target = "entityName", source = "entity.name")
    @Mapping(target = "entityId", source = "entity.id")
    TaskDto toDto(TaskEntity task);  // Changed parameter name from 'entity' for clarity

    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "entity", ignore = true)
    TaskEntity toEntity(TaskDto dto);
}