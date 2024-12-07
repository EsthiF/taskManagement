package com.example.taskmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private String taskStatus;
    private LocalDate dueDate;
    private String comment;
    private Long assignedManagerId;
    private String assignedManagerFirstName;
    private String assignedManagerLastName;
    private Long entityId;
    private String entityName;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getAssignedManagerId() {
        return assignedManagerId;
    }

    public void setAssignedManagerId(Long assignedManagerId) {
        this.assignedManagerId = assignedManagerId;
    }

    public String getAssignedManagerFirstName() {
        return assignedManagerFirstName;
    }

    public void setAssignedManagerFirstName(String assignedManagerFirstName) {
        this.assignedManagerFirstName = assignedManagerFirstName;
    }

    public String getAssignedManagerLastName() {
        return assignedManagerLastName;
    }

    public void setAssignedManagerLastName(String assignedManagerLastName) {
        this.assignedManagerLastName = assignedManagerLastName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
