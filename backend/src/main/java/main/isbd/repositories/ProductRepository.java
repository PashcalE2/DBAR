package main.isbd.repositories;

import main.isbd.data.model.Product;
import main.isbd.data.model.ProductId;
import main.isbd.data.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
    List<Product> findAllByTypeId(ProductType type);
}
