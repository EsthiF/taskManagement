
package com.example.taskmanagement.dto;

import com.example.taskmanagement.enums.Sector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String name;
    private String sector;
    private String email;
    private List<EntityDto> entities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<EntityDto> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityDto> entities) {
        this.entities = entities;
    }
}
