package com.example.taskmanagement.model;

import com.example.taskmanagement.model.ManagerEntity;
import com.example.taskmanagement.model.TaskEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EntityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TaskEntity> tasks;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private ManagerEntity manager;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;
    public void setClient(ClientEntity client) {
        this.client = client;
    }
    public void setManager(ManagerEntity manager) {
        this.manager = manager;
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

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public ManagerEntity getManager() {
        return manager;
    }

    public ClientEntity getClient() {
        return client;
    }
}
