package main.isbd.repositories;

import main.isbd.data.model.Cooperation;
import main.isbd.data.model.CooperationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CooperationRepository extends JpaRepository<Cooperation, CooperationId> {
}
