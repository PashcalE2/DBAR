package main.isbd.repositories;

import main.isbd.data.model.Order;
import main.isbd.data.model.ProductInOrder;
import main.isbd.data.model.ProductInOrderId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, ProductInOrderId> {
    List<ProductInOrder> findByOrderId_Id(Integer orderId_id);
}
