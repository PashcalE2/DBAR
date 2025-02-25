package main.isbd.repositories;

import main.isbd.data.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findById(int id);
    Optional<Admin> findByLogin(String login);
}
