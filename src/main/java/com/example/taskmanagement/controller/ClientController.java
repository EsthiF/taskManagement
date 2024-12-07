package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.ClientDto;
import com.example.taskmanagement.exceptions.ApplicationException;
import com.example.taskmanagement.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping("/import-clients-from-excel")
    public ResponseEntity<List<Long>> importClientsFromExcel(@RequestParam("file") MultipartFile excelFile) {
        List<Long> importedClientIds = clientService.importClientsFromExcel(excelFile);
        return ResponseEntity.ok(importedClientIds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) throws ApplicationException {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ClientDto> getClientByName(@PathVariable String name) throws ApplicationException {
        return ResponseEntity.ok(clientService.getClientByName(name));
    }

    @GetMapping("/sector/{sector}")
    public ResponseEntity<List<ClientDto>> getClientBySector(@PathVariable String sector) throws ApplicationException {
        return ResponseEntity.ok(clientService.getClientBySector(sector));
    }

    @PostMapping
    public ResponseEntity<Long> addClient(@RequestBody ClientDto clientDto) throws ApplicationException {
        return ResponseEntity.ok(clientService.addClient(clientDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) throws ApplicationException {
        return ResponseEntity.ok(clientService.updateClient(id, clientDto));
    }

    @GetMapping("/with-tasks")
    public ResponseEntity<List<ClientDto>> getClientsWithTasks() throws ApplicationException {
        return ResponseEntity.ok(clientService.getClientsWithTasks());
    }

    @GetMapping("/with-tasks/{clientName}")
    public ResponseEntity<ClientDto> getOneClientWithTask(@PathVariable String clientName) throws ApplicationException {
        return ResponseEntity.ok(clientService.getOneClientWithTask(clientName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) throws ApplicationException {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}