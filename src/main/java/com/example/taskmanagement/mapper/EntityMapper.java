package com.example.taskmanagement.mapper;

import com.example.taskmanagement.dto.EntityDto;
import com.example.taskmanagement.model.EntityEntity;
import com.example.taskmanagement.model.ManagerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface EntityMapper {

    @Mapping(target = "manager", source = "manager", qualifiedByName = "managerToString")
    EntityDto toDto(EntityEntity entity);

    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    EntityEntity toEntity(EntityDto dto);

    @Named("managerToString")
    default String managerToString(ManagerEntity manager) {
        if (manager == null) {
            return null;
        }
        return manager.getFirstName() + " " + manager.getLastName();
    }
}