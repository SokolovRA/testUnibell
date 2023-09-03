package com.example.unibelltest.service;

import com.example.unibelltest.dto.ContactDTO;
import com.example.unibelltest.enums.ContactType;
import com.example.unibelltest.exception.IncorrectArgumentException;
import com.example.unibelltest.mapper.ContactMapper;
import com.example.unibelltest.model.Client;
import com.example.unibelltest.model.Contact;
import com.example.unibelltest.repository.ClientRepository;
import com.example.unibelltest.repository.ContactRepository;
import com.example.unibelltest.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ContactServiceImplTest {

    @InjectMocks
    private ContactServiceImpl contactService;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ContactMapper contactMapper;

    @Test
    public void testAddContactToClientWithValidData() {

        Long clientId = 1L;
        ContactType contactType = ContactType.EMAIL;
        String contactValue = "john@example.com";
        Client client = new Client();
        client.setId(clientId);
        Contact contact = new Contact();
        contact.setClient(client);
        contact.setType(contactType);
        contact.setValue(contactValue);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(contactRepository.save(any())).thenReturn(contact);

        ContactDTO expectedContactDTO = new ContactDTO();
        expectedContactDTO.setType(contactType);
        expectedContactDTO.setValue(contactValue);

        when(contactMapper.toDto(any())).thenReturn(expectedContactDTO);


        ContactDTO addedContact = contactService.addContactToClient(clientId, contactType, contactValue);


        assertNotNull(addedContact);
        assertEquals(contactType, addedContact.getType());
        assertEquals(contactValue, addedContact.getValue());
    }

    @Test
    public void testAddContactToClientWithInvalidData() {

        Long clientId = 1L;
        ContactType contactType = null;
        String contactValue = null;


        assertThrows(IncorrectArgumentException.class, () -> contactService.addContactToClient(clientId, contactType, contactValue));
    }

    @Test
    public void testGetContactsByClient() {

        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        Contact contact1 = new Contact();
        contact1.setClient(client);
        Contact contact2 = new Contact();
        contact2.setClient(client);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(contactRepository.findByClient(client)).thenReturn(List.of(contact1, contact2));
        when(contactMapper.toDto(contact1)).thenReturn(new ContactDTO());
        when(contactMapper.toDto(contact2)).thenReturn(new ContactDTO());

        List<ContactDTO> contacts = contactService.getContactsByClient(clientId);

        assertNotNull(contacts);
        assertEquals(2, contacts.size());
    }

    @Test
    public void testGetContactsByClientAndType() {
        Long clientId = 1L;
        ContactType contactType = ContactType.PHONE;
        Client client = new Client();
        client.setId(clientId);
        Contact contact1 = new Contact();
        contact1.setClient(client);
        contact1.setType(contactType);
        Contact contact2 = new Contact();
        contact2.setClient(client);
        contact2.setType(contactType);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(contactRepository.findByClientAndType(client, contactType)).thenReturn(List.of(contact1, contact2));
        when(contactMapper.toDto(contact1)).thenReturn(new ContactDTO());
        when(contactMapper.toDto(contact2)).thenReturn(new ContactDTO());


        List<ContactDTO> contacts = contactService.getContactsByClientAndType(clientId, contactType);

        assertNotNull(contacts);
        assertEquals(2, contacts.size());
    }
}
