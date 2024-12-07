package com.example.taskmanagement.repository;

import com.example.taskmanagement.enums.Sector;
import com.example.taskmanagement.exceptions.ApplicationException;
import com.example.taskmanagement.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {


    Optional<ClientEntity> findByName(String name);

    @Query("SELECT c FROM ClientEntity c WHERE c.sector = :sector")
    List<ClientEntity> findBySector(Sector sector);

    @Query("SELECT c FROM ClientEntity c JOIN FETCH c.entities e JOIN FETCH e.tasks")
    List<ClientEntity> findAllClientsWithTasks();

    @Query("SELECT c FROM ClientEntity c JOIN FETCH c.entities e JOIN FETCH e.tasks WHERE c.name = :name")
    Optional<ClientEntity> findClientWithTasksByName(@Param("name") String name);

    boolean existsByEmail(String email);
}
