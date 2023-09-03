package com.example.unibelltest.controller;

import com.example.unibelltest.dto.ClientDTO;
import com.example.unibelltest.dto.ClientInformationDTO;
import com.example.unibelltest.service.ClientService;
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
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Клиенты", description = "Управление клиентами")
public class ClientController {
    private final ClientService clientService;

    @Operation(
            summary = "Добавить клиента",
            description = "Добавляет нового клиента в систему.",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Created",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            }
    )
    @PostMapping("/add")
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO addedClient = clientService.addClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedClient);
    }

    @Operation(
            summary = "Получить всех клиентов",
            description = "Получает список всех клиентов в системе.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDTO.class))}),
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clients = clientService.getClients();
        return ResponseEntity.ok(clients);
    }

    @Operation(
            summary = "Получить информацию о клиенте по ID",
            description = "Получает информацию о клиенте по указанному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientInformationDTO.class))}),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClientInformationDTO> getClientById(@PathVariable Long id) {
        ClientInformationDTO clientInfo = clientService.getClientById(id);
        return ResponseEntity.ok(clientInfo);
    }
}
