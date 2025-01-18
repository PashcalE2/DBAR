package main.isbd.repositories;

import main.isbd.data.model.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialTypeRepository extends JpaRepository<MaterialType, Integer> {
}
