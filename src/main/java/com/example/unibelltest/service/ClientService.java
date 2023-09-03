package com.example.unibelltest.service;

import com.example.unibelltest.dto.ClientDTO;
import com.example.unibelltest.dto.ClientInformationDTO;

import java.util.List;

public interface ClientService {
    ClientDTO addClient(ClientDTO clientDTO);

    List<ClientDTO> getClients();

    ClientInformationDTO getClientById(Long id);

}
