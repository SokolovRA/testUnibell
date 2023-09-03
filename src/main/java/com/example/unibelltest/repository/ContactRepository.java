package com.example.unibelltest.repository;

import com.example.unibelltest.enums.ContactType;
import com.example.unibelltest.model.Client;
import com.example.unibelltest.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> findByClient(Client client);
    List<Contact> findByClientAndType(Client client, ContactType type);
}
