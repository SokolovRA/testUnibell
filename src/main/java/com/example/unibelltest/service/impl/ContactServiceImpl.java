package com.example.unibelltest.service.impl;

import com.example.unibelltest.dto.ContactDTO;
import com.example.unibelltest.enums.ContactType;
import com.example.unibelltest.service.ContactService;

import java.util.List;

public class ContactServiceImpl implements ContactService {
    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        return null;
    }

    @Override
    public List<ContactDTO> getContactsByClient(Long clientId) {
        return null;
    }

    @Override
    public List<ContactDTO> getContactsByClientAndType(Long clientId, ContactType type) {
        return null;
    }
}
