package com.example.unibelltest.mapper;

import com.example.unibelltest.dto.ClientDTO;
import com.example.unibelltest.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {
    @Mapping(target = "name", source = "client.name")
    ClientDTO toDto(Client client);

    @Mapping(target = "name", source = "clientDTO.name")
    Client toEntity(ClientDTO clientDTO);
}


