package com.example.unibelltest.mapper;

import com.example.unibelltest.dto.ContactDTO;
import com.example.unibelltest.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ContactMapper {
    @Mapping(target = "value", source = "contact.value")
    @Mapping(target = "type", source = "contact.type")
    ContactDTO toDto(Contact contact);

    @Mapping(target = "value", source = "contactDTO.value")
    @Mapping(target = "type", source = "contactDTO.type")
    Contact toEntity(ContactDTO contactDTO);
}
