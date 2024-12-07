package com.example.taskmanagement.mapper;

import com.example.taskmanagement.dto.TaskTemplateDto;
import com.example.taskmanagement.model.TaskTemplateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskTemplateMapper {

    TaskTemplateDto toDto(TaskTemplateEntity entity);

    TaskTemplateEntity toEntity(TaskTemplateDto dto);
}
