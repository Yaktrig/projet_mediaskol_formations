package fr.mediaskol.projet.dal;

import fr.mediaskol.projet.bo.SessionFormation.SessionFormation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionFormationRepository extends JpaRepository<SessionFormation, Long> {
}
