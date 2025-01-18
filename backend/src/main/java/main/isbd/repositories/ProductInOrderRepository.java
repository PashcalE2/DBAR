package main.isbd.repositories;

import main.isbd.data.model.ProductInOrder;
import main.isbd.data.model.ProductInOrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, ProductInOrderId> {
}
