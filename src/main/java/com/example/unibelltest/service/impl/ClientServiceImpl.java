package com.example.unibelltest.service.impl;

import com.example.unibelltest.dto.ClientDTO;
import com.example.unibelltest.dto.ClientInformationDTO;
import com.example.unibelltest.exception.EntityNotFoundException;
import com.example.unibelltest.exception.IncorrectArgumentException;
import com.example.unibelltest.mapper.ClientMapper;
import com.example.unibelltest.mapper.ContactMapper;
import com.example.unibelltest.model.Client;
import com.example.unibelltest.repository.ClientRepository;
import com.example.unibelltest.repository.ContactRepository;
import com.example.unibelltest.service.ClientService;
import com.example.unibelltest.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    @Override
    public ClientDTO addClient(ClientDTO clientDTO) {
        if (clientDTO.getName() == null || clientDTO.getName().isBlank()) {
            throw new IncorrectArgumentException("Invalid client data: name is required");
        }
        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public List<ClientDTO> getClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientInformationDTO getClientById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return clientMapper.toInfoDto(client.get());
        }
        throw new EntityNotFoundException("Client not found with ID: " + id);
    }
}



