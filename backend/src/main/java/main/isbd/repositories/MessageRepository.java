package main.isbd.repositories;

import main.isbd.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByOrderId_Id(int id);
}
