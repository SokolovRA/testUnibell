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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;
    private final ContactMapper contactMapper;
    @Override
    public ContactDTO addContactToClient(Long clientId, ContactType type, String value) {
        if (type == null || value == null || value.isBlank()) {
            throw new IncorrectArgumentException("Invalid contact data: type and value are required");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));

        Contact contact = new Contact();
        contact.setClient(client);
        contact.setType(type);
        contact.setValue(value);

        contact = contactRepository.save(contact);
        return contactMapper.toDto(contact);
    }

    @Override
    public List<ContactDTO> getContactsByClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));
        List<Contact> contacts = contactRepository.findByClient(client);
        return contacts.stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDTO> getContactsByClientAndType(Long clientId, ContactType type) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));
        List<Contact> contacts = contactRepository.findByClientAndType(client, type);
        return contacts.stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }
}

