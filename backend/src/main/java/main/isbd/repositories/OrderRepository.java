package main.isbd.repositories;

import main.isbd.data.model.Client;
import main.isbd.data.model.Order;
import main.isbd.data.model.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByAdminId_Id(Integer adminId_id);
    List<Order> findAllByClientId(Client clientId);
    Optional<Order> findOneByClientIdAndStatus(Client clientId, OrderStatusEnum status);
}
