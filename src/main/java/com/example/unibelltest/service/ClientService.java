package com.example.unibelltest.service;

import com.example.unibelltest.dto.ClientDTO;

import java.util.List;

public interface ClientService {
    ClientDTO addClient(ClientDTO clientDTO);
    List<ClientDTO> getClients();
    ClientDTO getClientById(Long id);
}
