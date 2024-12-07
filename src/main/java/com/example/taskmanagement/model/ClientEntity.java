package com.example.taskmanagement.model;

import com.example.taskmanagement.enums.Sector;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private Sector sector;

    @Column( unique = true)
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EntityEntity> entities;

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEntities(List<EntityEntity> entities) {
        this.entities = entities;
    }

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

    public Sector getSector() {
        return sector;
    }

    public String getEmail() {
        return email;
    }

    public List<EntityEntity> getEntities() {
        return entities;
    }
}
