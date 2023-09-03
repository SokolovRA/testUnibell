package com.example.unibelltest.service;

import com.example.unibelltest.dto.ContactDTO;
import com.example.unibelltest.enums.ContactType;

import java.util.List;

public interface ContactService {
    ContactDTO addContactToClient(Long clientId, ContactType type, String value) ;
    List<ContactDTO> getContactsByClient(Long clientId);
    List<ContactDTO> getContactsByClientAndType(Long clientId, ContactType type);
}
