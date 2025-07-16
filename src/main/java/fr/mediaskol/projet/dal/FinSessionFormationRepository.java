package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.SessionFormation.FinSessionFormation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinSessionFormationRepository extends JpaRepository<FinSessionFormation, Long> {
}
