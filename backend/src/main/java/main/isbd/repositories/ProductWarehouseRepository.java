package main.isbd.repositories;

import main.isbd.data.model.ProductWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, Integer> {
}
