package com.example.taskmanagement.service;

import com.example.taskmanagement.enums.ErrorType;
import com.example.taskmanagement.dto.ClientDto;
import com.example.taskmanagement.enums.Sector;
import com.example.taskmanagement.exceptions.ApplicationException;
import com.example.taskmanagement.model.ClientEntity;
import com.example.taskmanagement.mapper.ClientMapper;
import com.example.taskmanagement.model.EntityEntity;
import com.example.taskmanagement.repository.ClientRepository;
import com.example.taskmanagement.utils.CommonValidations;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public List<Long> importClientsFromExcel(MultipartFile excelFile) {
        List<ClientDto> clientDtos = readClientsFromExcel(excelFile);
        return batchImportClients(clientDtos);
    }

    private List<ClientDto> readClientsFromExcel(MultipartFile excelFile) {
        List<ClientDto> clientDtos = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(excelFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                ClientDto clientDto = new ClientDto();

                // Check and read Name
                Cell nameCell = row.getCell(0);
                clientDto.setName(nameCell != null && nameCell.getCellType() == CellType.STRING
                        ? nameCell.getStringCellValue()
                        : "Unknown Name");

                Cell sectorCell = row.getCell(1); // Assuming the sector is in column index 1
                String sectorValue = sectorCell != null && sectorCell.getCellType() == CellType.STRING
                        ? sectorCell.getStringCellValue()
                        : "UNKNOWN";

                try {
                    clientDto.setSector(String.valueOf(Sector.valueOf(sectorValue.toUpperCase())));
                } catch (IllegalArgumentException e) {
                    clientDto.setSector(String.valueOf(Sector.UNKNOWN)); // Default to UNKNOWN if invalid
                    logger.warn("Invalid sector value: {}. Defaulting to UNKNOWN.", sectorValue);
                }


                clientDtos.add(clientDto);
            }
        } catch (IOException e) {
            logger.error("Error reading Excel file: {}", e.getMessage(), e);
        }

        return clientDtos;
    }


    private List<Long> batchImportClients(List<ClientDto> clientDtos) {
        List<ClientEntity> clientEntities = clientDtos.stream()
                .map(clientMapper::toEntity)
                .collect(Collectors.toList());

        List<ClientEntity> savedClients = clientRepository.saveAll(clientEntities);
        return savedClients.stream()
                .map(ClientEntity::getId)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getClientById(Long id) throws ApplicationException {
        Optional<ClientEntity> clientEntity = this.clientRepository.findById(id);
        if (clientEntity.isEmpty()) {
            throw new ApplicationException(ErrorType.CLIENT_NOT_FOUND);
        }
        return clientMapper.toDto(clientEntity.get());
    }

    @Override
    public List<ClientDto> getAllClients() {
        List<ClientEntity> clients = this.clientRepository.findAll();
        return clients.stream().map(clientMapper::toDto).toList();
    }

    @Override
    public ClientDto getClientByName(String name) throws ApplicationException {
        Optional<ClientEntity> clientEntity = this.clientRepository.findByName(name);
        if (clientEntity.isEmpty()) {
            throw new ApplicationException(ErrorType.CLIENT_NOT_FOUND);
        }
        return clientMapper.toDto(clientEntity.get());
    }

    @Override
    public List<ClientDto> getClientBySector(String sectorStr) throws ApplicationException {
        try {
            Sector sector = Sector.valueOf(sectorStr.toUpperCase());
            List<ClientEntity> clients = this.clientRepository.findBySector(sector);
            if (clients.isEmpty()) {
                throw new ApplicationException(ErrorType.CLIENT_NOT_FOUND);
            }
            return clients.stream()
                    .map(clientMapper::toDto)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new ApplicationException(ErrorType.INVALID_SECTOR, "Invalid sector value");
        }
    }

    @Override
    public Long addClient(ClientDto clientDto) throws ApplicationException {
        validateClient(clientDto);
        if (clientRepository.existsByEmail(clientDto.getEmail())) {
            throw new ApplicationException(ErrorType.DUPLICATE_EMAIL,
                    "The email " + clientDto.getEmail() + " already exists.");
        }
        ClientEntity clientEntity = clientMapper.toEntity(clientDto);

        if (clientEntity.getEntities() == null || clientEntity.getEntities().isEmpty()) {
            clientEntity.setEntities(new ArrayList<>());
        } else {
            clientEntity.getEntities().forEach(entity -> entity.setClient(clientEntity));
        }

        try {
            clientRepository.save(clientEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to add client", e);
        }

        return clientEntity.getId();
    }

    private void validateClient(ClientDto clientDto) throws ApplicationException {
        validateClientName(clientDto.getName());
        validateClientEmail(clientDto.getEmail());
    }

    private void validateClientEmail(String email) throws ApplicationException {
        if (!CommonValidations.validateEmailAddressStructure(email)) {
            throw new ApplicationException(ErrorType.COMPANY_EMAIL_INVALID);
        }
    }

    private void validateClientName(String name) throws ApplicationException {
        int validationResult = CommonValidations.validateStringLength(name, 2, 45);
        if (validationResult == 1) {
            throw new ApplicationException(ErrorType.CLIENT_NAME_TOO_LONG);
        }
        if (validationResult == -1) {
            throw new ApplicationException(ErrorType.CLIENT_NAME_TOO_SHORT);
        }
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto clientDto) throws ApplicationException {
        // Find existing client
        ClientEntity existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorType.CLIENT_NOT_FOUND,
                        "Client with ID " + id + " not found"));

        // Update name if provided
        if (clientDto.getName() != null) {
            validateClientName(clientDto.getName());
            existingClient.setName(clientDto.getName());
        }

        // Update sector if provided
        if (clientDto.getSector() != null) {
            existingClient.setSector(Sector.valueOf(clientDto.getSector())); // Convert String to enum
        }

        // Update email if provided
        if (clientDto.getEmail() != null) {
            validateClientEmail(clientDto.getEmail());
            existingClient.setEmail(clientDto.getEmail());
        }

        // Update entities if provided
        if (clientDto.getEntities() != null) {
            existingClient.setEntities(clientDto.getEntities().stream()
                    .map(entityDto -> {
                        EntityEntity entityEntity = new EntityEntity();
                        entityEntity.setId(entityDto.getId());
                        entityEntity.setName(entityDto.getName());
                        entityEntity.setClient(existingClient);
                        return entityEntity;
                    })
                    .toList());
        }

        // Save and return updated client
        try {
            ClientEntity updatedClient = clientRepository.save(existingClient);
            return clientMapper.toDto(updatedClient);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR,
                    "Failed to update client with ID " + id, e);
        }
    }

    @Override
    public List<ClientDto> getClientsWithTasks() throws ApplicationException {
        List<ClientEntity> clientsWithTasks = clientRepository.findAllClientsWithTasks();

        if (clientsWithTasks.isEmpty()) {
            throw new ApplicationException(ErrorType.NO_TASKS_FOUND, "No clients with tasks found.");
        }

        return clientsWithTasks.stream()
                .map(clientMapper::toDto)
                .toList();
    }

    @Override
    public ClientDto getOneClientWithTask(String clientName) throws ApplicationException {
        ClientEntity clientEntity = this.clientRepository.findClientWithTasksByName(clientName)
                .orElseThrow(() -> new ApplicationException(ErrorType.CLIENT_NOT_FOUND,
                        "Client with name '" + clientName + "' not found."));
        return clientMapper.toDto(clientEntity);
    }

    @Override
    public void deleteClient(Long id) throws ApplicationException {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorType.CLIENT_NOT_FOUND,
                        "Client with ID " + id + " not found"));
        try {
            clientRepository.delete(clientEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR,
                    "Failed to delete client with ID " + id, e);
        }
    }
}