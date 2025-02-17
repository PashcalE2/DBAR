package main.isbd.repositories;

import main.isbd.data.model.Message;
import main.isbd.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByOrderId_Id(int id);
    List<Message> findAllByOrderId(Order orderId);
}
