package com.main.auth.repositories;

import com.main.auth.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
  Optional<Client> findByLogin(String login);
}