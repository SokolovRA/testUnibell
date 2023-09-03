package com.example.unibelltest.controller;

import com.example.unibelltest.dto.ClientDTO;
import com.example.unibelltest.dto.ClientInformationDTO;
import com.example.unibelltest.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO addedClient = clientService.addClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedClient);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clients = clientService.getClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientInformationDTO> getClientById(@PathVariable Long id) {
        ClientInformationDTO clientInfo = clientService.getClientById(id);
        return ResponseEntity.ok(clientInfo);
    }
}
