package main.isbd.repositories;

import main.isbd.data.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Integer> {
}
