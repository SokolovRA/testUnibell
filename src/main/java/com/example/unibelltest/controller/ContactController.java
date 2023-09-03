package com.example.unibelltest.controller;

import com.example.unibelltest.dto.ContactDTO;
import com.example.unibelltest.enums.ContactType;
import com.example.unibelltest.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/add/{clientId}/{type}")
    public ResponseEntity<ContactDTO> addContactToClient(
            @PathVariable("clientId") Long clientId,
            @PathVariable("type") ContactType type,
            @RequestParam("value") String value
    ) {
        ContactDTO addedContact = contactService.addContactToClient(clientId, type, value);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedContact);
    }
    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<ContactDTO>> getContactsByClient(@PathVariable Long clientId) {
        List<ContactDTO> contacts = contactService.getContactsByClient(clientId);
        return ResponseEntity.ok(contacts);
    }
    @GetMapping("/by-client-and-type")
    public ResponseEntity<List<ContactDTO>> getContactsByClientAndType(
            @RequestParam(name = "clientId") Long clientId,
            @RequestParam(name = "type") ContactType type
    ) {
        List<ContactDTO> contacts = contactService.getContactsByClientAndType(clientId, type);
        return ResponseEntity.ok(contacts);
    }
}
