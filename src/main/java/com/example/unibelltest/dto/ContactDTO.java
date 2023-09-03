package com.example.unibelltest.dto;

import com.example.unibelltest.enums.ContactType;
import lombok.Data;

@Data
public class ContactDTO {
    private Long id;
    private String value;
    private ContactType type;

}
