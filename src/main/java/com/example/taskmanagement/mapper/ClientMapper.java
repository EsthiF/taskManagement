package com.example.taskmanagement.mapper;
import com.example.taskmanagement.dto.ClientDto;
import com.example.taskmanagement.mapper.EntityMapper;
import com.example.taskmanagement.model.ClientEntity;
import com.example.taskmanagement.enums.Sector;
import com.example.taskmanagement.model.ManagerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring", imports = {Sector.class}, uses = {EntityMapper.class})
public interface ClientMapper {

    @Mapping(target = "sector", expression = "java(entity.getSector().name())")
    @Mapping(target = "entities", source = "entities")
    ClientDto toDto(ClientEntity entity);

    @Mapping(target = "sector", expression = "java(com.example.taskmanagement.enums.Sector.valueOf(dto.getSector()))")
    @Mapping(target = "entities", ignore = true)
    ClientEntity toEntity(ClientDto dto);
    @Named("managerToString")
    default String managerToString(ManagerEntity manager) {
        if (manager == null) {
            return null;
        }
        return manager.getFirstName() + " " + manager.getLastName();
    }
}