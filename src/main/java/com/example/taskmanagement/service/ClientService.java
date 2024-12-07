package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.ClientDto;
import com.example.taskmanagement.enums.Sector;
import com.example.taskmanagement.exceptions.ApplicationException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClientService {
    ClientDto getClientById(Long id) throws ApplicationException;

    List<ClientDto> getAllClients();

    ClientDto getClientByName(String name) throws ApplicationException;

    List<ClientDto> getClientBySector(String sectorStr) throws ApplicationException;

    Long addClient(ClientDto clientDto) throws ApplicationException;

    ClientDto updateClient(Long id, ClientDto clientDto) throws ApplicationException;

    List<ClientDto> getClientsWithTasks() throws ApplicationException;

    ClientDto getOneClientWithTask(String clientName) throws ApplicationException;
    List<Long> importClientsFromExcel(MultipartFile excelFile);

    void deleteClient(Long id) throws ApplicationException;
}