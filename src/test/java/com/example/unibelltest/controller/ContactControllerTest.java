package com.example.unibelltest.controller;

import com.example.unibelltest.dto.ClientDTO;
import com.example.unibelltest.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ClientDTO client1 = new ClientDTO();
        client1.setName("John");
        clientService.addClient(client1);

        ClientDTO client2 = new ClientDTO();
        client2.setName("Alice");
        clientService.addClient(client2);
    }

    @Test
    public void testAddContactToClient() throws Exception {
        Long clientId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/contacts/add/" + clientId + "/EMAIL")
                        .param("value", "john@example.com"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetContactsByClient() throws Exception {
        Long clientId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts/by-client/" + clientId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetContactsByClientAndType() throws Exception {
        Long clientId = 1L;
        String contactType = "EMAIL";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts/by-client-and-type")
                        .param("clientId", clientId.toString())
                        .param("type", contactType))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRemoveNonExistentContact() throws Exception {
        Long contactId = 999L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/contacts/" + contactId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetContactsByNonExistentClient() throws Exception {
        Long clientId = 999L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts/by-client/" + clientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetContactsByInvalidType() throws Exception {
        Long clientId = 1L;
        String contactType = "INVALID_TYPE";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts/by-client-and-type")
                        .param("clientId", clientId.toString())
                        .param("type", contactType))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}

