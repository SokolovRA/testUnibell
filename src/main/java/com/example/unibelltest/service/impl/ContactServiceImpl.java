package com.example.unibelltest.service.impl;

import com.example.unibelltest.dto.ContactDTO;
import com.example.unibelltest.enums.ContactType;
import com.example.unibelltest.exception.EntityNotFoundException;
import com.example.unibelltest.exception.IncorrectArgumentException;
import com.example.unibelltest.mapper.ContactMapper;
import com.example.unibelltest.model.Client;
import com.example.unibelltest.model.Contact;
import com.example.unibelltest.repository.ClientRepository;
import com.example.unibelltest.repository.ContactRepository;
import com.example.unibelltest.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;
    private final ContactMapper contactMapper;

    @Override
    public ContactDTO addContactToClient(Long clientId, ContactType type, String value) {
        log.info("Adding a new contact to client (Client ID={}, Type={}, Value={})", clientId, type, value);

        if (type == null || value == null || value.isBlank()) {
            log.error("Invalid contact data: type and value are required");
            throw new IncorrectArgumentException("Invalid contact data: type and value are required");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    log.error("Client not found with ID: {}", clientId);
                    return new EntityNotFoundException("Client not found with ID: " + clientId);
                });

        Contact contact = new Contact();
        contact.setClient(client);
        contact.setType(type);
        contact.setValue(value);

        contact = contactRepository.save(contact);

        log.info("Contact added successfully: ID={}, Type={}, Value={}", contact.getId(), contact.getType(), contact.getValue());

        return contactMapper.toDto(contact);
    }

    @Override
    public List<ContactDTO> getContactsByClient(Long clientId) {
        log.info("Fetching contacts by client (Client ID={})", clientId);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    log.error("Client not found with ID: {}", clientId);
                    return new EntityNotFoundException("Client not found with ID: " + clientId);
                });

        List<Contact> contacts = contactRepository.findByClient(client);
        List<ContactDTO> contactDTOs = contacts.stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());

        log.info("Fetched {} contacts for client (Client ID={})", contactDTOs.size(), clientId);

        return contactDTOs;
    }

    @Override
    public List<ContactDTO> getContactsByClientAndType(Long clientId, ContactType type) {
        log.info("Fetching contacts by client and type (Client ID={}, Type={})", clientId, type);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    log.error("Client not found with ID: {}", clientId);
                    return new EntityNotFoundException("Client not found with ID: " + clientId);
                });

        List<Contact> contacts = contactRepository.findByClientAndType(client, type);
        List<ContactDTO> contactDTOs = contacts.stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());

        log.info("Fetched {} contacts of type {} for client (Client ID={})", contactDTOs.size(), type, clientId);

        return contactDTOs;
    }
}
