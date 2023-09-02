package com.example.unibelltest.repository;

import com.example.unibelltest.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ClientRepository extends JpaRepository <Client,Long> {
}
