package main.isbd.repositories;

import main.isbd.data.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findById(int id);
    Admin findByIdAndPassword(Integer id, String password);
}
