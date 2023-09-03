package com.example.unibelltest.controller;

import com.example.unibelltest.dto.ContactDTO;
import com.example.unibelltest.enums.ContactType;
import com.example.unibelltest.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@Tag(name = "Контакты", description = "Управление контактами клиентов")
public class ContactController {
    private final ContactService contactService;

    @Operation(
            summary = "Добавить контакт клиенту",
            description = "Добавляет новый контакт указанного типа клиенту с заданным значением.",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Created",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ContactDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            }
    )
    @PostMapping("/add/{clientId}/{type}")
    public ResponseEntity<ContactDTO> addContactToClient(
            @PathVariable("clientId") Long clientId,
            @PathVariable("type") ContactType type,
            @RequestParam("value") String value
    ) {
        ContactDTO addedContact = contactService.addContactToClient(clientId, type, value);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedContact);
    }

    @Operation(
            summary = "Получить контакты клиента",
            description = "Получает список всех контактов указанного клиента.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ContactDTO.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            }
    )
    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<ContactDTO>> getContactsByClient(@PathVariable Long clientId) {
        List<ContactDTO> contacts = contactService.getContactsByClient(clientId);
        return ResponseEntity.ok(contacts);
    }

    @Operation(
            summary = "Получить контакты клиента по типу",
            description = "Получает список контактов указанного клиента по заданному типу.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ContactDTO.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            }
    )
    @GetMapping("/by-client-and-type")
    public ResponseEntity<List<ContactDTO>> getContactsByClientAndType(
            @RequestParam(name = "clientId") Long clientId,
            @RequestParam(name = "type") ContactType type
    ) {
        List<ContactDTO> contacts = contactService.getContactsByClientAndType(clientId, type);
        return ResponseEntity.ok(contacts);
    }
}
