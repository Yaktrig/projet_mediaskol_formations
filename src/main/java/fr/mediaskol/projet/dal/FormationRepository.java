package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.formation.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationRepository extends JpaRepository<Formation, Long> {
}
