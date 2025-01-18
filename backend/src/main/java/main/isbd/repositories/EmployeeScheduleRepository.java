package main.isbd.repositories;

import main.isbd.data.model.EmployeeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule, Integer> {
}
