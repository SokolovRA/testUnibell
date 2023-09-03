package com.example.unibelltest.service;

import com.example.unibelltest.dto.ClientDTO;
import com.example.unibelltest.dto.ClientInformationDTO;
import com.example.unibelltest.exception.EntityNotFoundException;
import com.example.unibelltest.exception.IncorrectArgumentException;
import com.example.unibelltest.mapper.ClientMapper;
import com.example.unibelltest.model.Client;
import com.example.unibelltest.repository.ClientRepository;
import com.example.unibelltest.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void testAddClientWithValidData() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("John Doe");

        Client clientEntity = new Client();
        clientEntity.setName("John Doe");

        when(clientMapper.toEntity(clientDTO)).thenReturn(clientEntity);
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);
        when(clientMapper.toDto(clientEntity)).thenReturn(clientDTO);

        ClientDTO addedClient = clientService.addClient(clientDTO);

        assertNotNull(addedClient);
        assertEquals("John Doe", addedClient.getName());
    }

    @Test
    public void testAddClientWithInvalidData() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("");

        assertThrows(IncorrectArgumentException.class, () -> clientService.addClient(clientDTO));
    }

    @Test
    public void testGetClients() {
        List<Client> clientEntities = new ArrayList<>();

        Client client1 = new Client();
        client1.setName("John Doe");
        clientEntities.add(client1);

        Client client2 = new Client();
        client2.setName("Alice Smith");
        clientEntities.add(client2);

        when(clientRepository.findAll()).thenReturn(clientEntities);
        when(clientMapper.toDto(client1)).thenReturn(new ClientDTO());
        when(clientMapper.toDto(client2)).thenReturn(new ClientDTO());

        List<ClientDTO> clientDTOs = clientService.getClients();

        assertEquals(2, clientDTOs.size());

    }

    @Test
    public void testGetClientByIdWithValidId() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setName("John Doe");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        ClientInformationDTO expectedClientInfo = new ClientInformationDTO();
        when(clientMapper.toInfoDto(client)).thenReturn(expectedClientInfo);

        ClientInformationDTO clientInfo = clientService.getClientById(clientId);

        assertNotNull(clientInfo);
        assertEquals(expectedClientInfo, clientInfo);
    }

    @Test
    public void testGetClientByIdWithInvalidId() {
        Long clientId = 999L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(clientId));
    }

    @Test
    public void testGetClientByIdNotFound() {
        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(clientId));
    }
}
