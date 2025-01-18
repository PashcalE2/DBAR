package main.isbd.repositories;

import main.isbd.data.model.Factory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactoryRepository extends JpaRepository<Factory, Integer> {
}
