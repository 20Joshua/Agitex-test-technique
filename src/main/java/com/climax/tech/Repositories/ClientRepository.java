package com.climax.tech.Repositories;

import com.climax.tech.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAll();

    List<Client> findByProfession(String profession);
}

