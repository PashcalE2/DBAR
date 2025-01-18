package main.isbd.repositories;

import main.isbd.data.model.AdminSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminScheduleRepository extends JpaRepository<AdminSchedule, Integer> {
}
