package main.isbd.repositories;

import main.isbd.data.model.Product;
import main.isbd.data.model.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
}
