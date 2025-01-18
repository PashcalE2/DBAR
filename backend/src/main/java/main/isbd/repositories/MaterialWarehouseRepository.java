package main.isbd.repositories;

import main.isbd.data.model.MaterialWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialWarehouseRepository extends JpaRepository<MaterialWarehouse, Integer> {
}
