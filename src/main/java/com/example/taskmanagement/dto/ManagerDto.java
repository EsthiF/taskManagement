package com.example.taskmanagement.dto;

import com.example.taskmanagement.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Long seniorManagerId;
    private String seniorManagerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getSeniorManagerId() {
        return seniorManagerId;
    }

    public void setSeniorManagerId(Long seniorManagerId) {
        this.seniorManagerId = seniorManagerId;
    }

    public String getSeniorManagerName() {
        return seniorManagerName;
    }

    public void setSeniorManagerName(String seniorManagerName) {
        this.seniorManagerName = seniorManagerName;
    }
}
