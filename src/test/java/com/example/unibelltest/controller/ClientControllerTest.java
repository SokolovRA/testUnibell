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
public class ClientControllerTest {
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
        client1.setName("John Doe");
        clientService.addClient(client1);

        ClientDTO client2 = new ClientDTO();
        client2.setName("Alice Smith");
        clientService.addClient(client2);
    }

    @Test
    public void testAddClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/add").contentType("application/json").content("{\"name\": \"John Doe\"}")).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetAllClients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/all")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetClientById() throws Exception {
        Long clientId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + clientId)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddClientWithInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/add").contentType("application/json").content("{\"name\": \"\"}")).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetNonExistentClient() throws Exception {
        Long clientId = 100L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/" + clientId)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

