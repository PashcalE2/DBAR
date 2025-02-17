package main.isbd.repositories;

import main.isbd.data.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByNameAndPassword(String name, String password);
}
