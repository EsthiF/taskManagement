package com.example.taskmanagement.mapper;

import com.example.taskmanagement.dto.ManagerDto;
import com.example.taskmanagement.model.ManagerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface ManagerMapper {

    @Mapping(target = "seniorManagerId", source = "seniorManager.id")
    @Mapping(target = "seniorManagerName", source = "seniorManager", qualifiedByName = "getFullName")
    @Mapping(target = "role", expression = "java(manager.getRole().name())")
    ManagerDto toDto(ManagerEntity manager);

    @Mapping(target = "seniorManager", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "role", expression = "java(com.example.taskmanagement.enums.Role.valueOf(dto.getRole()))")
    ManagerEntity toEntity(ManagerDto dto);

    @Named("getFullName")
    default String getFullName(ManagerEntity manager) {
        if (manager == null) {
            return null;
        }
        return manager.getFirstName() + " " + manager.getLastName();
    }
}