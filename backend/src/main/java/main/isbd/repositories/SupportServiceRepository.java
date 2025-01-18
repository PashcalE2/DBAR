package main.isbd.repositories;

import main.isbd.data.model.SupportService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportServiceRepository extends JpaRepository<SupportService, Integer> {
}
