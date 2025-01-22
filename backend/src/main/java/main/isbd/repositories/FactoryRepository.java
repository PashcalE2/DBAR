package main.isbd.repositories;

import main.isbd.data.model.Factory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FactoryRepository extends JpaRepository<Factory, Integer> {
    Optional<Factory> findByIdAndPassword(Integer id, String password);
}
