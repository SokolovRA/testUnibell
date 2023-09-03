package com.example.unibelltest.mapper;

import com.example.unibelltest.dto.ClientDTO;
import com.example.unibelltest.dto.ClientInformationDTO;
import com.example.unibelltest.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {

    @Mapping(target = "id", source = "client.id")
    @Mapping(target = "name", source = "client.name")
    @Mapping(target = "contacts", source = "client.contacts")
    ClientInformationDTO toInfoDto(Client client);

    @Mapping(target = "id", source = "client.id")
    @Mapping(target = "name", source = "client.name")
    ClientDTO toDto(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "clientDTO.name")
    Client toEntity(ClientDTO clientDTO);
}

