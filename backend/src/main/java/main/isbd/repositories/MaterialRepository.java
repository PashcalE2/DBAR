package main.isbd.repositories;

import main.isbd.data.model.Material;
import main.isbd.data.model.MaterialId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, MaterialId> {
}
