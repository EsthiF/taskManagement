package com.example.taskmanagement.model;

import com.example.taskmanagement.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private ManagerEntity manager; // Manager overseeing this task

    @ManyToOne
    @JoinColumn(name = "task_template_id", nullable = false)
    private TaskTemplateEntity taskTemplate;

    @ManyToOne
    @JoinColumn(name = "entity_id", nullable = false)
    private EntityEntity entity;


    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(length = 1000)
    private String comment;
}
