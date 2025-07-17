package fr.mediaskol.projet.dal.sessionFormation;

import fr.mediaskol.projet.bo.sessionFormation.FinSessionFormation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinSessionFormationRepository extends JpaRepository<FinSessionFormation, Long> {
}
